package myutil;

public class FormatString {

	private final String addCommaForQuery(String[] stringFields, String addStr) {
        return addPunctuationAndComma(stringFields, addStr, "");
    }

    private final String addCommaForInsert(String[] stringFields) {
        return addPunctuationAndComma(stringFields, "'", "'");
    }

    private final String addPunctuationAndComma(String[] stringFields, String startStr, String endStr) {
        String tempResult = startStr + stringFields[0] + endStr; //put the initial field in the string
        for (int x = 1; x < stringFields.length; x++) {
            tempResult += "," + startStr + stringFields[x] + endStr;
        }
        return tempResult;
    }
    
    private final String makeConditionStatementFromVariable(String[] condition,
            String[] conditionValue, String connector) { //from variable because sql20 needs symbol : in the condition value
        String tempResult = "";
        for (int x = 0; x < condition.length; x++) {
            tempResult += " " + condition[x] + " = :" + conditionValue[x] + " " + connector;
        }
        tempResult = tempResult.substring(0, tempResult.length() - 4); //minus 4 to remove the and
        return tempResult;
    }
    
    //Public Declaration
    
    public String addOrderByInQuery(String sql, String[] stringFields) {
    	return sql + " ORDER BY " + addCommaForQuery(stringFields, "") + " ASC";
    }
    
    public String addDeleteFlag(String sql){
    	return sql + " and deleted=1"; //only for tables with delete flag
    }
    
    public String queryString(String tableName, String isDistinct, String[] commaFields,
            String[] condition, String[] conditionValue, String connector) {
        String SQL = "SELECT " + isDistinct + addCommaForQuery(commaFields, "");
        SQL += " FROM " + tableName + " WHERE";
        SQL += makeConditionStatementFromVariable(condition, conditionValue, connector);
        return SQL;
    }
}
