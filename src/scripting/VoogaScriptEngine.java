package scripting;

import backend.game_engine.ResultQuadPredicate;
import backend.player.Player;
import backend.unit.UnitInstance;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.InteractionModifier;
import backend.util.*;
import util.io.Serializer;
import util.io.Unserializer;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 4/6/2017.
 */
public abstract class VoogaScriptEngine implements Serializer, Unserializer, InteractionModifier.Modifier, TriggeredEffectInstance.Effect, ActiveAbility.AbilityEffect, ResultQuadPredicate, BiPredicate<Player, ImmutableGameState> {
    private static final HashMap<String, String> SCRIPT_ENGINE_IDENTIFIERS = new HashMap<>();

    static {
        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("javascript", "js", "nashorn", "ecmascript").collect(Collectors.toMap(e -> e, e -> "JavaScript")));
        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("groovy", "groovyshell", "apachegroovy").collect(Collectors.toMap(e -> e, e -> "Groovy")));
        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("java").collect(Collectors.toMap(e -> e, e -> "Java")));
        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("python", "py", "jython").collect(Collectors.toMap(e -> e, e -> "Python")));
        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("lua", "luaj").collect(Collectors.toMap(e -> e, e -> "Lua")));
//        SCRIPT_ENGINE_IDENTIFIERS.putAll(Stream.of("slogo", "lol").collect(Collectors.toMap(e -> e, e -> "Slogo")));
    }

    private final String scriptContent;

    protected VoogaScriptEngine(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public static Collection<String> getAllSupportedScriptingLanguages() {
        return SCRIPT_ENGINE_IDENTIFIERS.values();
    }

    public static VoogaScriptEngine read(String language, Path path) throws IOException {
        return read(language, new String(Files.readAllBytes(path)));
    }

    public static VoogaScriptEngine read(String langauge, String script) throws VoogaScriptException {
        langauge = SCRIPT_ENGINE_IDENTIFIERS.get(langauge.replace(" ", "").toLowerCase());
        try {
            return (VoogaScriptEngine) Class.forName(String.format("Vooga%sEngine", langauge)).getConstructor(String.class).newInstance(script);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new VoogaScriptException("Language not supported");
        }
    }

    protected final String formatFunction(String functionFormat, String functionName, Collection<String> paramNames) {
        return String.format(functionFormat, functionName, paramNames.toString().substring(1, paramNames.toString().length() - 1), scriptContent.replaceAll("(?m)^", "\t"));
    }

    final String getScript() {
        return scriptContent;
    }

    protected abstract Object eval(Map<String, Object> bindings) throws VoogaScriptException;

    private HashMap<String, Object> createBindings(Object... params) {
        HashMap<String, Object> bindings = new LinkedHashMap<>();
        try {
            for (int i = 0; i < params.length; i += 2) {
                bindings.put((String) params[i], params[i + 1]);
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            throw new Error("Invalid arguments, must be name, binding, name, binding, name binding etc.");
        }
        return bindings;
    }

    @Override
    public Result determine(Player player, MutableGameState gameState) {
        try {
            return Result.valueOf((String) eval(createBindings("player", player, "gameState", gameState)));
        } catch (ClassCastException e) {
            throw new VoogaScriptException(e);
        }
    }

    @Override
    public Object doUnserialize(Serializable serializableObject) throws VoogaScriptException {
        try {
            return eval(createBindings("serializable", serializableObject));
        } catch (ClassCastException e) {
            throw new VoogaScriptException(e);
        }
    }

    @Override
    public Serializable doSerialize(Object object) throws VoogaScriptException {
        try {
            return (Serializable) eval(createBindings("object", object));
        } catch (ClassCastException e) {
            throw new VoogaScriptException(e);
        }
    }

    @Override
    public void affect(UnitInstance unit, Event event, ImmutableGameState gameState) {
        eval(createBindings("unit", unit, "event", event, "gameState", gameState));
    }

    @Override
    public void useAbility(UnitInstance user, VoogaObject target, ImmutableGameState gameState) {
        eval(createBindings("abilityUser", user, "abilityTarget", target, "gameState", gameState));
    }

    @Override
    public Object modify(Object originalValue, UnitInstance agent, UnitInstance target, ImmutableGameState gameState) {
        return eval(createBindings("originalValue", originalValue, "agent", agent, "target", target, "gameState", gameState));
    }

    @Override
    public boolean test(Player player, ImmutableGameState immutableGameState) {
        Object nonBooleanValue = eval(createBindings("player", player, "gameState", immutableGameState));
        if (nonBooleanValue instanceof String) {
            return !nonBooleanValue.equals("");
        } else if (nonBooleanValue instanceof Boolean) {
            return (Boolean) nonBooleanValue;
        } else {
            return Objects.nonNull(nonBooleanValue);
        }
    }

    static class VoogaScriptException extends RuntimeException {
        VoogaScriptException(Exception e) {
            super(e);
        }

        public VoogaScriptException(String s) {
            super(s);
        }
    }
}
