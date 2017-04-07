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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 4/6/2017.
 */
public abstract class VoogaScriptEngine implements Serializer, Unserializer, InteractionModifier.Modifier, TriggeredEffectInstance.Effect, ActiveAbility.AbilityEffect, ResultQuadPredicate, BiPredicate<Player, ImmutableGameState> {
    private static final Map<String, VoogaScriptEngine> SCRIPT_ENGINES = new HashMap<>();
    private String script;

    protected VoogaScriptEngine() {
        script = "";
    }

    public static VoogaScriptEngine getScriptEngine(String name) {
        return SCRIPT_ENGINES.get(name.toLowerCase());
    }

    public final VoogaScriptEngine read(String script) {
        this.script = script;
        return this;
    }

    public final VoogaScriptEngine read(Path path) throws IOException {
        return read(new String(Files.readAllBytes(path)));
    }

    protected static void addEngine(VoogaScriptEngine engine, String... identifiers) {
        for (String identifier : identifiers) {
            SCRIPT_ENGINES.put(identifier, engine);
        }
    }

    final String getScript() {
        return script;
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
    public Object doUnserialize(Serializable serializableObject) throws Exception {
        try {
            return eval(createBindings("serializable", serializableObject));
        } catch (ClassCastException e) {
            throw new VoogaScriptException(e);
        }
    }

    @Override
    public Serializable doSerialize(Object object) throws Exception {
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
    }
}
