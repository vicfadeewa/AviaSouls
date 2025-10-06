import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // проверяем, что найденные билеты сортируются от дешевых к дорогим
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "KZN", 5000, 10, 12)); // Третий
        manager.add(new Ticket("LED", "KZN", 3000, 11, 13)); // Первый
        manager.add(new Ticket("LED", "KZN", 4000, 12, 14)); // Второй

        Ticket[] result = manager.search("LED", "KZN");
        assertEquals(3000, result[0].getPrice()); // Самый дешёвый первый
        assertEquals(4000, result[1].getPrice()); // Средний
        assertEquals(5000, result[2].getPrice()); // Самый дорогой последний
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
        // проверяем сортировку билетов от быстрых к медленным
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "KZN", 5000, 10, 12)); // 2 часа
        manager.add(new Ticket("LED", "KZN", 3000, 11, 15)); // 4 часа
        manager.add(new Ticket("LED", "KZN", 4000, 9, 10));  // 1 час

        Ticket[] result = manager.searchAndSortBy("LED", "KZN", new TicketTimeComparator());
        assertEquals(1, result[0].getTimeTo() - result[0].getTimeFrom()); // Самый быстрый первый (1 час)
        assertEquals(2, result[1].getTimeTo() - result[1].getTimeFrom()); // Второй (2 часа)
        assertEquals(4, result[2].getTimeTo() - result[2].getTimeFrom()); // Самый долгий последний (4 часа)
    }

    @Test
    public void testFindTicket() {
        // проверяем поиск билета по маршруту
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "SVO", 2000, 8, 10));
        manager.add(new Ticket("LED", "KZN", 3000, 11, 13));
        manager.add(new Ticket("SVO", "LED", 2500, 9, 11));

        Ticket[] result = manager.search("LED", "KZN");
        assertEquals(1, result.length); // найден только один билет по маршруту LED-KZN
        assertEquals("LED", result[0].getFrom());
        assertEquals("KZN", result[0].getTo());
        assertEquals(3000, result[0].getPrice());
    }

    @Test
    public void testNoMatchingTickets() {
        // тест на отсутствие билетов при несовпадающем маршруте
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "KZN", 3000, 10, 12));
        Ticket[] result = manager.search("SVO", "LED");
        assertEquals(0, result.length);
    }


}