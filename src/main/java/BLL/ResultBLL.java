package BLL;

import DAL.ResultDAL;

public class ResultBLL {
    private final ResultDAL resultDAL = new ResultDAL();
    
    public int getTotalResult() {
        return resultDAL.getTotalResult();
    }
    
    public int getTotalResultBelow50() {
        return resultDAL.getTotalResultBelow50();
    }
    
    public int getTotalResultAbove50() {
        return resultDAL.getTotalResultAbove50();
    }
    
    public int getTotalResultsByTestId(int testId) {
        return resultDAL.getTotalResultsByTestId(testId);
    }
    
    public int getTotalResultsAbove50ByTestId(int testId) {
        return resultDAL.getTotalResultsAbove50ByTestId(testId);
    }
    
    public int getTotalResultsBelow50ByTestId(int testId) {
        return resultDAL.getTotalResultsBelow50ByTestId(testId);
    }
            
    public double getMarkTBTestId(int testId) {
        return resultDAL.getMarkTBTestId(testId);
    }
    
    public int getTotalResultsByMonth(int testId) {
        return resultDAL.getTotalResultsByMonth(testId);
    }
    
    public double getAverageMarkByMonth(int testId) {
        return resultDAL.getAverageMarkByMonth(testId);
    }
}
