package pokemon.logic;

import pokemon.logic.exceptions.AttackException;
import pokemon.utils.Language;

/**
 * The class <code>Attack</code> manages the attack of <code>Pokemon</code>s.
 * Each <code>Attack</code> has:
 *      Names: the names of <code>Attack</code> in different languages.
 *      EffectDescriptions: the descriptions of <code>Attack</code> in different languages.
 *      Power: the power of <code>Attack</code>
 * 
 * @author Marco Russodivito
 */
public class Attack {
    /**
     * The <code>Pokemon</code> hit himself.
     */
    public static final int TARGET_SELF = 0;
    /**
     * The <code>Pokemon</code> hit the other pokemon.
     */
    public static final int TARGET_OTHER_POKEMON = 1;
      
    /**
     * The names of <code>Attack</code> in different languages.
     */
    protected String names[];
    /**
     * The descriptions of <code>Attack</code>'s effect in different languages.
     */
    protected String effectDescription[];
    /**
     * The power of the <code>Attack</code> - PRE-CONDITIONS: must be positive or zero.
     */
    protected int power;
    /**
     * The target of the <code>Attack</code> - PRE-CONDITIONS: must be <code>TARGET_SELF</code> or <code>TARGET_OTHER_POKEMON</code>.
     */
    protected int target;
    /**
     * The percentage of hp to recover - PRE-CONDITIONS: must be between 0 and 100. 
     */
    protected int hpPercentageToRecover; 
           
    /**
     * Creates a <code>Attack</code>.
     * 
     * @param pNames the names of <code>Attack</code> in different languages.
     * @param pEffectDescriptio the descriptions of <code>Attack</code>'s effect in different languages.
     * @param pPower the power of the <code>Attack</code>.
     * @param pTarget the target of the <code>Attack</code>.
     * @param pHPRecoved the percentage of hp to recover
     * @throws AttackException if parameters don't respect PRE-CONDITIONS.
     */
    public Attack(String[] pNames, String[] pEffectDescriptio, int pPower, int pTarget, int pHPRecoved) throws AttackException {
        if (pPower< 0)
            throw new AttackException("Invalid Power");
        
        if ( !(pTarget == Attack.TARGET_OTHER_POKEMON || pTarget == Attack.TARGET_SELF) )
            throw new AttackException("Invalid target");
            
        if( pHPRecoved < 0 || pHPRecoved > 100) 
            throw new AttackException("Invalid percentage");
        
        this.names = pNames;
        this.effectDescription = pEffectDescriptio;
        this.power = pPower;
        this.target = pTarget;
        this.hpPercentageToRecover = pHPRecoved;
    }
    
    /**
     * Returns the name of <code>Attack</code> in the current language.
     * 
     * @return the name of <code>Attack</code> in the current language.
     */
    public String getName() {
        return this.names[Language.getLanguage()];
    }

    /**
     * Returns the description <code>Attack</code>'s effect in the current language.
     * 
     * @return the description <code>Attack</code>'s effect in the current language.
     */
    public String getEffectDescription() {
        return this.effectDescription[Language.getLanguage()];
    }
    
    /**
     * Returns the power of the <code>Attack</code>.
     * 
     * @return the power of the <code>Attack</code>.
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Returns the target of the <code>Attack</code>.
     * 
     * @return the target of the <code>Attack</code>.
     */
    public int getTarget() {
        return this.target;
    }
    
    /**
     * Returns the percentage of hp to recover.
     * 
     * @return the percentage of hp to recover.
     */
    public int getHPRecoved() {
        return this.hpPercentageToRecover;
    }
    
}
