package app;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ejb.Calculation;

@Stateless
public class CalculationService {

    @PersistenceContext(unitName="hello")
    private EntityManager entityManager;

    public int performCalculation(int number1, int number2, String operation) {
        int result = 0;
        if (operation.equals("+")) {
            result = number1 + number2;
        } else if (operation.equals("-")) {
            result = number1 - number2;
        } else if (operation.equals("*")) {
            result = number1 * number2;
        } else if (operation.equals("/")) {
            if (number2 != 0) {
                result = number1 / number2;
            } else {
                throw new IllegalArgumentException("Division by zero");
            }
        } else {
            throw new IllegalArgumentException("Invalid operation");
        }

        saveCalculation(number1, number2, operation);

        return result;
    }

    private void saveCalculation(int number1, int number2, String operation) {
        Calculation calculation = new Calculation(number1, number2, operation);
        entityManager.persist(calculation);
    }

    public List<Calculation> getAllCalculations() {
        TypedQuery<Calculation> query = entityManager.createQuery("SELECT c FROM Calculation c", Calculation.class);
        return query.getResultList();
    }
	
}
