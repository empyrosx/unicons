/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation.forms.rules.parsers;

import consolidation.forms.rules.base.BaseRule;

/**
 * ѕарсер контрольных соотношений
 * @param <E> класс контрольных соотношений, поддерживаемых этим парсером
 * @author ƒимитрий
 */
public interface RuleParser<E extends BaseRule> {


    /**
     * –азбор правил
     * @param rules контрольные соотношений в строковом виде
     * @return набор контрольных соотношений
     */
    public E[] parseRules(String rules);
}
