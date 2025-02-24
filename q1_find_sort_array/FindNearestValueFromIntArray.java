public class FindNearestValueFromIntArray {

    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 4, 6, 78, 13115, 27846, 28659, 31864, 45850, 46990, 50348, 56801, 57012, 58689, 65791, 68582, 75620, 82813, 83246, 86478, 93916, 97878, 98546, 115726, 117442, 126552, 127277, 131831, 142242, 165592, 167062, 167328, 176001, 182802, 187515, 190866, 210307, 227780, 233227, 250777, 257913, 259910, 261968, 270884, 271476, 271864, 272357, 276608, 279466, 280391, 280660, 290851, 313772, 315817, 316559, 319627, 333862, 337528, 345264, 354584, 361383, 379808, 387293, 391417, 392027, 395734, 399999, 403849, 403928, 406964, 411277, 411812, 412330, 415357, 417029, 417410, 420730, 425231, 428206, 430394, 432257, 434040, 434334, 446419, 449131, 449945, 451602, 462961, 470349, 470594, 475050, 476707, 477391, 481456, 485823, 495097, 497424, 500588, 501713, 502800, 506625, 520383, 531255, 540934, 542550, 543333, 544017, 552251, 558652, 577488, 580450, 582677, 585264, 592474, 595446, 602260, 603171, 604098, 607736, 608861, 609284, 609316, 620901, 639080, 640316, 643411, 647786, 658578, 659177, 661624, 662547, 667014, 667425, 671852, 672339, 679145, 683227, 683765, 688732, 690967, 700701, 712716, 716370, 718620, 720554, 721972, 724347, 726186, 731091, 732599, 737835, 741075, 748075, 748217, 750664, 758103, 758818, 759292, 762055, 764318, 765800, 768007, 778309, 781858, 783445, 787275, 792845, 794333, 796389, 798032, 800648, 805983, 806026, 814502, 818524, 827833, 829437, 841939, 843251, 843467, 846166, 859565, 863599, 863985, 869928, 874611, 881021, 882085, 883960, 895349, 898732, 903295, 915900, 920336, 923816, 942129, 944229, 946153, 948866, 951348, 975322, 980304, 984802, 992204, 1000000};
        int target = args.length > 0 ? Integer.parseInt(args[0]) : 900000;
        System.out.println("Nearest value of " + target + " that found in array is " + findNearestValueOfArray(arr, target));
    }

    /**
     * Find Nearest Value of {@code int[]} with binary-search algorithm
     * @param arr     int[] data
     * @param target  target that you want to find
     **/
    public static int findNearestValueOfArray(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int nearest = arr[0]; // Store the nearest value

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Update the nearest value if the current mid is nearer
            if (Math.abs(arr[mid] - target) < Math.abs(nearest - target)) {
                nearest = arr[mid];
            }

            if (arr[mid] == target) {
                return arr[mid]; // Found exact match
            } else if (arr[mid] < target) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        return nearest; // Return the nearest value
    }
}
