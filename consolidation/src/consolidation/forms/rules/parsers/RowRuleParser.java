package consolidation.forms.rules.parsers;

import consolidation.forms.rules.base.RuleSign;
import consolidation.forms.rules.row.RowRule;
import consolidation.forms.rules.row.RowRuleImpl;
import consolidation.forms.rules.row.RowRuleItemImpl;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Парсер колоночных контрольных соотношений
 * @author Димитрий
 */
public class RowRuleParser implements RuleParser<RowRule> {

    private ArrayList<String> ExtractTokens(String rule) throws IOException {

        Reader r = new StringReader(rule);

        StreamTokenizer stream = new StreamTokenizer(r);
        stream.resetSyntax();
        stream.wordChars('0', '9');

        ArrayList<String> result = new ArrayList<String>();

        String sign = "";
        for (int currentToken = stream.nextToken(); currentToken != StreamTokenizer.TT_EOF;
                currentToken = stream.nextToken()) {

            switch (currentToken) {
                case StreamTokenizer.TT_WORD:
                    if (!sign.isEmpty()) {
                        result.add(sign.trim());
                        sign = "";
                    }
                    result.add(stream.sval);
                    break;
                default:
                    if (stream.ttype > 0) {
                        sign = sign + (char) stream.ttype;
                    }
                    break;
            }
        }
        return result;
    }

    public RowRule parseRule(String rule) {

        //System.out.println(rule);
        ArrayList<String> ruleTokens = null;
        String ruleCaption = "";
        try {
            String newRule = "";
            
            String[] lines = rule.split("\n");
            for (int j = 0; j < lines.length; j++){
                String r = lines[j];
                if (r.startsWith("//$")){
                    ruleCaption = r.substring(4);
                }
                else {
                    newRule = r;
                }
            }

            if (newRule.isEmpty()){
                return null;
            }

            ruleTokens = ExtractTokens(newRule);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ruleTokens != null) {

            RowRuleImpl rowRule = new RowRuleImpl(ruleCaption, new RowRuleItemImpl(ruleTokens.get(0), RuleSign.parse(ruleTokens.get(1))));

            RuleSign sign = RuleSign.Plus;

            for (int i = 2; i < ruleTokens.size(); i = i + 2) {
                String columnName = ruleTokens.get(i);
                rowRule.add(new RowRuleItemImpl(columnName, sign));
                if (i + 1 < ruleTokens.size()) {
                    sign = RuleSign.parse(ruleTokens.get(i + 1));
                }
            }

            return rowRule;
        } else {
            return null;
        }

    }

    @Override
    public RowRule[] parseRules(String rules) {

        //System.out.println(rules);
        ArrayList<RowRule> result = new ArrayList<RowRule>();

        String[] rulesSet = rules.split(";");
        for (int i = 0; i < rulesSet.length; i++) {
            String rule = rulesSet[i].trim();
            if (!rule.isEmpty()) {
                RowRule rowRule = parseRule(rule);
                if (rowRule != null) {
                    result.add(rowRule);
                }
            }
        }

        return result.toArray(new RowRule[result.size()]);
    }
}
