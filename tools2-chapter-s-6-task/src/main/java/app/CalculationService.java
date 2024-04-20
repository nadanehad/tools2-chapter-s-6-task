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
        switch (operation) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 != 0) {
                    result = number1 / number2;
                } else {
                    throw new IllegalArgumentException("Division by zero");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operation");
        }

        Calculation calculation = new Calculation(number1, number2, operation);
        entityManager.persist(calculation);

        return result;
    }
    public List<Calculation> getAllCalculations() {
        TypedQuery<Calculation> query = entityManager.createQuery("SELECT c FROM Calculation c", Calculation.class);
        return query.getResultList();
    }
	
}
