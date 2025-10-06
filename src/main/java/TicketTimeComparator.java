import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {
    /**
     * Сравнивает билеты по времени полёта
     *
     * @param t1 первый билет
     * @param t2 второй билет
     * @return отрицательное число если время t1 < t2, положительное если t1 > t2
     */
    @Override
    public int compare(Ticket t1, Ticket t2) {
        int time1 = t1.getTimeTo() - t1.getTimeFrom();
        int time2 = t2.getTimeTo() - t2.getTimeFrom();
        return Integer.compare(time1, time2);
    }
}
