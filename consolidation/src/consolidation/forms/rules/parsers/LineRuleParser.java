package consolidation.forms.rules.parsers;

import consolidation.forms.rules.base.RuleSign;
import consolidation.forms.rules.line.LineRule;
import consolidation.forms.rules.line.LineRuleImpl;
import consolidation.forms.rules.line.LineRuleItemImpl;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import ru.diman.description.matrix.DataSet;

public class LineRuleParser {

    // матрица шаблона
    private DataSet matrix;

    public LineRuleParser(DataSet matrix) {
        this.matrix = matrix;
    }

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
                    if (sign != "") {
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

    public LineRule parseRule(String rule) {
        try {
            ArrayList<String> ruleTokens = null;
            try {
                ruleTokens = ExtractTokens(rule);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (ruleTokens != null) {

                int keyValue = Integer.parseInt(ruleTokens.get(0));
                List<Object> keyValues = new ArrayList<Object>();
                keyValues.add(keyValue);
                int rowNumber = matrix.findRow(keyValues);

                LineRuleImpl lineRule = new LineRuleImpl("", new LineRuleItemImpl(rowNumber, RuleSign.parse(ruleTokens.get(1))));

                RuleSign sign = RuleSign.Plus;

                for (int i = 2; i < ruleTokens.size(); i = i + 2) {

                    keyValue = Integer.parseInt(ruleTokens.get(i));
                    keyValues = new ArrayList<Object>();
                    keyValues.add(keyValue);
                    rowNumber = matrix.findRow(keyValues);

                    lineRule.add(new LineRuleItemImpl(rowNumber, sign));
                    if (i + 1 < ruleTokens.size()) {
                        sign = RuleSign.parse(ruleTokens.get(i + 1));
                    }
                }

                return lineRule;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LineRule[] parseRules(String rules) {
        ArrayList<LineRule> result = new ArrayList<LineRule>();

        String[] rulesSet = rules.split(";");
        for (int i = 0; i < rulesSet.length; i++) {
            String rule = rulesSet[i].trim();
            if (!rule.isEmpty()) {
                LineRule lineRule = parseRule(rule);
                if (lineRule != null) {
                    result.add(lineRule);
                }
            }
        }

        return result.toArray(new LineRule[result.size()]);
    }
}
