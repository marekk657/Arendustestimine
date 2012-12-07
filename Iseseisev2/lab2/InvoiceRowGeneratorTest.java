package lab2;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.laughingpanda.beaninject.Inject;

public class InvoiceRowGeneratorTest {


    @Test
    public void calculateInvoiceRowSumTest() throws Exception {

        InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);

        InvoiceRowGenerator generator = new InvoiceRowGenerator();

        Inject.bean(generator).with(invoiceRowDao); // Throws exception

        generator.generateRowsFor(10, asDate("2012-02-15"), asDate("2012-04-02"));

        verify(invoiceRowDao, times(2)).save(argThat(getMatcherForSum(new BigDecimal(3))));
        verify(invoiceRowDao).save(argThat(getMatcherForSum(new BigDecimal(4))));

        // verify that there are no more calls
    	verifyNoMoreInteractions(invoiceRowDao);
    }
    
    @Test
    public void calculateInvoiceRowDatesTest() throws Exception {
    	InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);
    	
    	InvoiceRowGenerator generator = new InvoiceRowGenerator();
    	
        Inject.bean(generator).with(invoiceRowDao); // Throws exception
    	generator.generateRowsFor(15, asDate("2012-02-15"), asDate("2012-06-02"));
    	
    	// Should be enough to ensure that every date has only one Invoice row generated
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-02-15"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-03-01"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-04-01"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-05-01"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-06-01"))));
    	
    	verifyNoMoreInteractions(invoiceRowDao);
    }
    
    @Test
    public void remainingSumTest_dividedCorrectly() throws Exception {
    	InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);
    	
    	InvoiceRowGenerator generator = new InvoiceRowGenerator();
    	
    	Inject.bean(generator).with(invoiceRowDao); // Throws exception

    	generator.generateRowsFor(9, asDate("2012-02-15"), asDate("2012-04-02"));

    	verify(invoiceRowDao, times(3)).save(argThat(getMatcherForSum(new BigDecimal(3))));
    	verifyNoMoreInteractions(invoiceRowDao);
    }
    
    
    @Test
    public void remainingSumTest_lessThanThree_returnsSum () throws Exception {
    	InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);
    	
    	InvoiceRowGenerator generator = new InvoiceRowGenerator();
    	
    	Inject.bean(generator).with(invoiceRowDao); // Throws exception

    	generator.generateRowsFor(2, asDate("2012-02-15"), asDate("2012-04-02"));
    	
    	verify(invoiceRowDao).save(argThat(getMatcherForSum(new BigDecimal(2))));
    	verifyNoMoreInteractions(invoiceRowDao);
    }
    
    
    @Test
    public void remainingSumTest_sumsLastPayment () throws Exception {
    	InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);
    	
    	InvoiceRowGenerator generator = new InvoiceRowGenerator();
    	
    	Inject.bean(generator).with(invoiceRowDao); // Throws exception

    	generator.generateRowsFor(13, asDate("2012-02-15"), asDate("2012-04-02"));

    	verify(invoiceRowDao, times(2)).save(argThat(getMatcherForSum(new BigDecimal(4))));
    	verify(invoiceRowDao).save(argThat(getMatcherForSum(new BigDecimal(5))));
    	verifyNoMoreInteractions(invoiceRowDao);
    }

    private Matcher<InvoiceRow> getMatcherForSum(final BigDecimal bigDecimal) {
		return new BaseMatcher<InvoiceRow> () {

			@Override
			public boolean matches(Object arg0) {
				InvoiceRow invoiceRow = (InvoiceRow) arg0;
				//BigDecimali korral kasutame compareTo Eq asemel loengu andmetel
				return bigDecimal.compareTo(invoiceRow.amount)==0;
			}

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText("Sum does not match");				
			}
		};
	}
    
    private Matcher<InvoiceRow> getMatcherForDate(final Date date) {
    	return new BaseMatcher<InvoiceRow> () {

			@Override
			public boolean matches(Object arg0) {
				
				Date otherDate = ((InvoiceRow)arg0).date;
				Calendar rowCalendar = Calendar.getInstance();
				rowCalendar.setTime(otherDate);
				int rowDay = rowCalendar.get(Calendar.DAY_OF_MONTH);
				int rowMonth = rowCalendar.get(Calendar.MONTH);
				
				Calendar checkCalendar = Calendar.getInstance();
				checkCalendar.setTime(date);
				int checkDay = checkCalendar.get(Calendar.DAY_OF_MONTH);
				int checkMonth = checkCalendar.get(Calendar.MONTH);
				
				return rowDay == checkDay && rowMonth == checkMonth;
			}

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText("Date does not match");				
			}
    		
    	};
    }

	public static Date asDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}