package lab2;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.laughingpanda.beaninject.Inject;
import org.mockito.internal.verification.VerificationModeFactory;

public class InvoiceRowGenerator {

    @Test
    public void calculateInvoiceRowSumTest() throws Exception {

        InvoiceRowDao invoiceRowDao = mock(InvoiceRowDao.class);

        InvoiceRowGenerator generator = new InvoiceRowGenerator();

//        Inject.bean(generator).with(invoiceRowDao); // Throws exception

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
    	
//      Inject.bean(generator).with(invoiceRowDao); // Throws exception
    	
    	generator.generateRowsFor(10, asDate("2012-02-15"), asDate("2012-04-02"));
    	
    	// Should be enough to ensure that every date has only one Invoice row generated
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-02-15"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-03-01"))));
    	verify(invoiceRowDao).save(argThat(getMatcherForDate(asDate("2012-04-01"))));
    	
    	verifyNoMoreInteractions(invoiceRowDao);
    }
    
    @Test
    public void remainingSumTest() throws Exception {
    	// TODO: !!
    }

    private Matcher<InvoiceRow> getMatcherForSum(final BigDecimal bigDecimal) {
		return new BaseMatcher<InvoiceRow> () {

			@Override
			public boolean matches(Object arg0) {
				InvoiceRow invoiceRow = (InvoiceRow) arg0;
				return bigDecimal.equals(invoiceRow.amount);
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
				Date otherDate = (Date)arg0;
				return date.equals(otherDate);
			}

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText("Date does not match");				
			}
    		
    	};
    }

	private void generateRowsFor(int i, Date asDate, Date asDate2) {
		// TODO Auto-generated method stub
	}

	public static Date asDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}