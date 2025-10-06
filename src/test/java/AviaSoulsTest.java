import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AviaSoulsTest {
    @Test
    public void testTicketCompareTo() {
        // проверяем, что дорогие билеты "больше" дешёвых
        Ticket t1 = new Ticket("A", "B", 5000, 10, 12);
        Ticket t2 = new Ticket("A", "B", 3000, 11, 13);

        assertTrue(t1.compareTo(t2) > 0); // 5000 > 3000
        assertTrue(t2.compareTo(t1) < 0); // 3000 < 5000
    }

    @Test
    public void testSearchSortByPrice() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket1 = new Ticket("LED", "KZN", 3000, 11, 13); // 1-й по цене
        Ticket ticket2 = new Ticket("LED", "KZN", 4000, 12, 14); // 2-й
        Ticket ticket3 = new Ticket("LED", "KZN", 5000, 10, 12); // 3-й

        manager.add(ticket3);
        manager.add(ticket1);
        manager.add(ticket2);

        Ticket[] expected = {ticket1, ticket2, ticket3};
        Ticket[] result = manager.search("LED", "KZN");

        assertArrayEquals(expected, result);
    }


    @Test
    public void testTimeComparator() {
        // проверяем, что билеты сравниваются по времени полёта
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket t1 = new Ticket("A", "B", 5000, 10, 12); // 2 часа в воздухе
        Ticket t2 = new Ticket("A", "B", 3000, 11, 15); // 4 часа в воздухе

        assertTrue(comparator.compare(t1, t2) < 0); // 2 часа < 4 часов
    }

    @Test
    public void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket1 = new Ticket("LED", "KZN", 4000, 9, 10);   // 1 час
        Ticket ticket2 = new Ticket("LED", "KZN", 5000, 10, 12);  // 2 часа
        Ticket ticket3 = new Ticket("LED", "KZN", 3000, 11, 15);  // 4 часа

        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket1);

        Ticket[] expected = {ticket1, ticket2, ticket3};
        Ticket[] result = manager.searchAndSortBy("LED", "KZN", new TicketTimeComparator());

        assertArrayEquals(expected, result);
    }

    @Test
    public void testFindTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket expectedTicket = new Ticket("LED", "KZN", 3000, 11, 13);

        manager.add(new Ticket("LED", "SVO", 2000, 8, 10));
        manager.add(expectedTicket);
        manager.add(new Ticket("SVO", "LED", 2500, 9, 11));

        Ticket[] expected = {expectedTicket};
        Ticket[] result = manager.search("LED", "KZN");

        assertArrayEquals(expected, result);
    }

    @Test
    public void testNoMatchingTickets() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "KZN", 3000, 10, 12));

        Ticket[] expected = new Ticket[0];
        Ticket[] result = manager.search("SVO", "LED");

        assertArrayEquals(expected, result);
    }
}