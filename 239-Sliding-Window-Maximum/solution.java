public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return nums;
        }
        return heapSolution(nums, k, n);
    }
    
    /**Solution1: Deque, amortized O(N) time*/
    public int[] dequeSolution(int[] nums, int k, int n) {
        int[] result = new int[n-k+1];
        // store index of element
        Deque<Integer> queue = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            // if current peek element index is out of sliding window, poll out.
            if (!queue.isEmpty() && queue.peek() < i-k+1) {
                queue.poll();
            }
            // poll out all indices whose values are no greater than current element value
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            // after first k iteration, add every max element to result
            if (i-k+1 >= 0) {
                result[i-k+1] = nums[queue.peek()];
            }
        }
        return result;
    }
    
    /**Solution2: maxHeap, O(Nlogk) time*/
    public int[] heapSolution(int[] nums, int k, int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> b-a);
        int[] result = new int[n-k+1];
        
        for (int i = 0; i < k; i++) {
            queue.offer(nums[i]);
        }
        result[0] = queue.peek();   
        
        for (int i = k; i < n; i++) {
            queue.remove(nums[i-k]);
            queue.offer(nums[i]);
            result[i-k+1] = queue.peek();
        }
        return result;
    }
}