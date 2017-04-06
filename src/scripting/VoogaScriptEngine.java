package scripting;

import backend.game_engine.ResultQuadPredicate;
import backend.player.Player;
import backend.unit.UnitInstance;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.InteractionModifier;
import backend.util.*;
import util.io.Serializer;
import util.io.Unserializer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
public abstract class VoogaScriptEngine implements Serializer, Unserializer, InteractionModifier.Modifier, TriggeredEffectInstance.Effect, ActiveAbility.AbilityEffect, ResultQuadPredicate {
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
        return (Result) eval(createBindings("player", player, "gameState", gameState));
    }

    @Override
    public Object doUnserialize(Serializable serializableObject) throws Exception {
        return eval(createBindings("serializable", serializableObject));
    }

    @Override
    public Serializable doSerialize(Object object) throws Exception {
        return (Serializable) eval(createBindings("object", object));
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

    static class VoogaScriptException extends RuntimeException {
        VoogaScriptException(Exception e) {
            super(e);
        }
    }
}
