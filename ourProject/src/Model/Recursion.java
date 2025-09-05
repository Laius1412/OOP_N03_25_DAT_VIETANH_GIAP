package Model;

public class Recursion {
    public static void sortPersonsByAgeAsc(Person[] people) {
        if (people == null || people.length <= 1) return;
        quickSortByAge(people, 0, people.length - 1, true);
    }

    public static void sortPersonsByAgeDesc(Person[] people) {
        if (people == null || people.length <= 1) return;
        quickSortByAge(people, 0, people.length - 1, false);
    }

    private static void quickSortByAge(Person[] people, int low, int high, boolean asc) {
        if (low >= high) return;
        int pivotIndex = partition(people, low, high, asc);
        quickSortByAge(people, low, pivotIndex - 1, asc);
        quickSortByAge(people, pivotIndex + 1, high, asc);
    }

    private static int partition(Person[] people, int low, int high, boolean asc) {
        int pivotAge = computeAge(people[high]);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            int age = computeAge(people[j]);
            boolean shouldSwap = asc ? age <= pivotAge : age >= pivotAge;
            if (shouldSwap) {
                i++;
                swap(people, i, j);
            }
        }
        swap(people, i + 1, high);
        return i + 1;
    }

    private static int computeAge(Person p) {
        if (p == null || p.getDob() == null) return Integer.MIN_VALUE; // push nulls to start/end consistently
        return p.getDob().getYear(); // earlier year => older
    }

    private static void swap(Person[] arr, int i, int j) {
        if (i == j) return;
        Person tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
