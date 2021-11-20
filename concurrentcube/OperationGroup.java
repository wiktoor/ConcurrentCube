package concurrentcube;

import java.util.ArrayList;

public class OperationGroup {
    private Cube cube;
    private ArrayList<Operation> list;

    public OperationGroup(Cube cube) {
        this.cube = cube;
        this.list = new ArrayList<Operation>();
    }

    public synchronized boolean tryJoin(Operation op) {
        for (Operation operation : list) {
            if (!operation.canWorkConcurrently(op)) {
                return false;
            }
        }
        list.add(op);
        return true;
    }
}
