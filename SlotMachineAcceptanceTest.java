import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineAcceptanceTest {

    @Test
    public void acceptanceTest1() {
        // 1. Crear la máquina
        SlotMachine machine = new SlotMachine();

        // 2. Agregar símbolos
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    
        // 3. Agregar tres ruedas
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        
        machine.makeVisible();

        // 4. Establecer configuración inicial
        machine.spin(new String[]{"red", "blue", "green"});

        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        // 5. Bloquear la primera rueda
        machine.lock(1);

        // Intentar girar una rueda bloqueada
        machine.spin(1, 2);

        // No debe cambiar
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        // 6. Desbloquear la primera rueda
        machine.unlock(1);

        // 7. Girar la primera rueda dos pasos
        machine.spin(1, 2);

        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );

        // 8. Establecer una nueva configuración
        machine.spin(new String[]{"red", "red", "red"});

        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );

        // 9. Comprobar jackpot
        assertTrue(machine.isJackpot());

        // 10. Intercambiar la primera y tercera rueda
        machine.swap(1, 3);

        // Como todas tienen red, sigue siendo jackpot
        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );

        assertTrue(machine.isJackpot());

        // 11. Probar una configuración diferente
        machine.spin(new String[]{"green", "blue", "red"});

        assertFalse(machine.isJackpot());

        // 12. Comprobar que la última operación fue correcta
        assertTrue(machine.ok());
    }
    
    @Test
    public void acceptanceTest2() {
        // 1. Crear la máquina
        SlotMachine machine = new SlotMachine();
    
        // 2. Agregar símbolos
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addSymbol(4, "yellow");
    
        // 3. Agregar cuatro ruedas
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addWheel(4);
        
        machine.makeVisible();
    
        // 4. Establecer configuración inicial
        machine.spin(new String[]{"red", "blue", "green", "yellow"});
    
        assertArrayEquals(
            new String[]{"red", "blue", "green", "yellow"},
            machine.configuration()
        );
    
        // 5. Girar la segunda rueda tres pasos
        machine.spin(2, 3);
    
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        // 6. Bloquear la tercera rueda
        machine.lock(3);
    
        // Intentar girar la tercera rueda
        machine.spin(3, 2);
    
        // No debe cambiar porque está bloqueada
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        // 7. Desbloquear la tercera rueda
        machine.unlock(3);
    
        // Girarla una vez
        machine.spin(3);
    
        assertArrayEquals(
            new String[]{"red", "red", "yellow", "yellow"},
            machine.configuration()
        );
    
        // 8. Intercambiar la primera y cuarta rueda
        machine.swap(1, 4);
    
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    
        // 9. Intentar establecer una configuración con
        // un símbolo que NO existe
        machine.spin(new String[]{"red", "purple", "yellow", "blue"});
    
        // La operación debe fallar
        assertFalse(machine.ok());
    
        // La configuración anterior debe mantenerse
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    }
}