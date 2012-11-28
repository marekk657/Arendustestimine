package lab2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InvoiceRowGenerator {
	
	private InvoiceRowDao invoiceRowDao;

	public void generateRowsFor(int sum, Date start, Date end) {
		List<Date> dates = getInvoiceDays(start, end);
		int rows = dates.size();
		if(sum <=3 || sum / rows < 3){
			InvoiceRow row = new InvoiceRow(new BigDecimal(sum), dates.get(0));
			invoiceRowDao.save(row);
		}else{
			BigDecimal[] divSum = getAmount(sum, rows);
			for(int i = 0 ; i < rows-1; i++){
				Date d = dates.get(i);
				InvoiceRow row = new InvoiceRow(divSum[0], d);
				invoiceRowDao.save(row);
			}
			BigDecimal last = divSum[1].add(divSum[0]);
			InvoiceRow row = new InvoiceRow(last, dates.get(rows-1));
			invoiceRowDao.save(row);
		}
	}
	private BigDecimal[] getAmount(int sum, int rows){
		BigDecimal[] divSum = new BigDecimal(sum).divideAndRemainder(new BigDecimal(rows), MathContext.DECIMAL128);
		return divSum;
	}
	
	/*private int getMonthsBetweenDAtes(Date start, Date end){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(start);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		return (c2.get(Calendar.YEAR)- c1.get(Calendar.YEAR)) *12 + (c2.get(Calendar.MONTH)- c1.get(Calendar.MONTH)) +1;
	}
	*/
	private List<Date> getInvoiceDays(Date start, Date end){
		List<Date> res = new ArrayList<Date>();
		res.add(start);
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(start);
		int startYear = c1.get(Calendar.YEAR);
		int startMonth = c1.get(Calendar.MONTH);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		int endYear = c2.get(Calendar.YEAR);
		int endMonth = c2.get(Calendar.MONTH);
		
		Calendar tmp = Calendar.getInstance();
		int cnt = 1;
		while(true){
			if((startYear + cnt / 12 == endYear) && (startMonth + cnt % 12 > endMonth)) {
				break;
			}
			tmp.set(Calendar.YEAR, startYear + cnt / 12);
			tmp.set(Calendar.MONTH, startMonth + cnt % 12);
			tmp.set(Calendar.DAY_OF_MONTH, 1);
			res.add(tmp.getTime());
			cnt +=1;
		}
		
		return res;
	}
	
}