import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tree {
    // We recommend attempting this class last, as it hasn't been scaffolded for your team.
    // Even if your team doesn't have time to implement this class, it is a useful exercise
    // to think about how you might split up the work to get the Tree and TreeMultiSet
    // implemented.
    private Integer root;
    private ArrayList<Tree> subtrees = new ArrayList<>();

    public Tree() {}

    public Tree(int root) {
        this.root = root;
    }

    public Tree(int root, ArrayList<Tree> subtrees) {
        this.root = root;
        this.subtrees = subtrees;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        if (isEmpty()) {
            return 0;
        } else {
            int size = 1;

            for (Tree subtree : subtrees) {
                size += subtree.getSize();
            }

            return size;
        }
    }

    public int count(int item) {
        if (isEmpty()) {
            return 0;
        } else {
            int num = 0;

            if (root == item) {
                num++;
            }

            for (Tree subtree : subtrees) {
                num += subtree.count(item);
            }

            return num;
        }
    }

    @Override
    public String toString() {
        return toStringIndented(0);
    }

    private String toStringIndented(int depth) {
        if (isEmpty()) {
            return "";
        } else {
            StringBuilder string = new StringBuilder("  ".repeat(depth) + "\n");

            for (Tree subtree : subtrees) {
                string.append(subtree.toStringIndented(depth + 1));
            }

            return new String(string);
        }
    }

    public float average() {
        AverageTuple result = averageHelper();

        return (float) result.total / result.size;
    }

    private class AverageTuple {
        private int total;
        private int size;

        public AverageTuple(int sum, int size) {
            this.total = sum;
            this.size = size;
        }
    }

    private AverageTuple averageHelper() {
        if (isEmpty()) {
            return new AverageTuple(0, 0);
        } else {
            int total = root;
            int size = 1;

            for (Tree subtree : subtrees) {
                AverageTuple result = subtree.averageHelper();

                total += result.total;
                size += result.size;
            }

            return new AverageTuple(total, size);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tree)) {
            return false;
        }

        Tree other = (Tree) obj;

        if (isEmpty() && other.isEmpty()) {
            return true;
        } else if (isEmpty() || other.isEmpty()) {
            return false;
        } else {
            if (!root.equals(other.root)) {
                return false;
            }

            return subtrees.equals(other.subtrees);
        }
    }

    public boolean contains(int item) {
        if (isEmpty()) {
            return false;
        }

        if (root == item) {
            return true;
        } else {
            for (Tree subtree : subtrees) {
                if (subtree.contains(item)) {
                    return true;
                }
            }

            return false;
        }
    }

    public ArrayList<Integer> leaves() {
        if (isEmpty()) {
            return new ArrayList<>();
        } else if (subtrees.isEmpty()) {
            return new ArrayList<>(Arrays.asList(root));
        } else {
            ArrayList<Integer> list = new ArrayList<>();

            for (Tree subtree : subtrees) {
                list.addAll(subtree.leaves());
            }

            return list;
        }
    }

    public boolean deleteItem(int item) {
        if (isEmpty()) {
            return false;
        } else if (root == item) {
            deleteRoot();

            return true;
        } else {
            for (Tree subtree : subtrees) {
                if (subtree.deleteItem(item)) {
                    if (subtree.isEmpty()) {
                        subtrees.remove(subtree);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private void deleteRoot() {
        if (subtrees.isEmpty()) {
            root = null;
        } else {
            Tree chosenSubtree = subtrees.removeLast();

            root = chosenSubtree.root;
            subtrees.addAll(chosenSubtree.subtrees);
        }
    }

    public void insert(int item) {
        if (isEmpty()) {
            root = item;
        } else if (subtrees.isEmpty()) {
            subtrees.add(new Tree(item));
        } else {
            if (new Random().nextInt(3) == 2) {
                subtrees.add(new Tree(item));
            } else {
                int subtreeIndex = new Random().nextInt(subtrees.size());

                subtrees.get(subtreeIndex).insert(item);
            }
        }
    }

    public boolean insertChild(int item, int parent) {
        if (isEmpty()) {
            return false;
        } else if (root == parent) {
            subtrees.add(new Tree(item));

            return true;
        } else {
            for (Tree subtree : subtrees) {
                if (subtree.insertChild(item, parent)) {
                    return true;
                }
            }

            return false;
        }
    }
}
