/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hanoi;

/**
 *
 * @author Estudiante
 */
public class Cola {

    private Nodo top;
    private int count = 0;

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public String top() throws MyException {
        return top.getDato();
    }

    public String push(String dato) throws MyException {
        String result = "";

        Nodo temp = new Nodo();
        temp.setDato(dato);
        if (top == null) {
            result += temp.getDato();
            temp.setSiguiente(null);
            top = temp;
            count++;
        } else {
            result += temp.getDato();
            temp.setSiguiente(top);
            top = temp;
            count++;
        }
        return result;
    }

    public String pop() throws MyException {
        String result = "";

        if (top != null) {
            result = top.getDato();
            top = top.getSiguiente();
            count--;
            return result;
        }
        return result;

    }
}
