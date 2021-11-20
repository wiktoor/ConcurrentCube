package concurrentcube;

import java.util.ArrayList;

public class OperationGroup {
    private Cube cube;
    private ArrayList<Operation> list;

    public OperationGroup(Cube cube) {
        this.cube = cube;
        this.list = new ArrayList<Operation>();
    }

    public OperationGroup(Cube cube, Operation op) {
        this.cube = cube;
        this.list = new ArrayList<Operation>();
        list.add(op);
    }

    public boolean canJoin(Operation op) {
        for (Operation operation : list) {
            if (!operation.canWorkConcurrently(op)) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean tryJoin(Operation op) {
        if (canJoin(op)) {
            list.add(op);
            return true;
        }
        else return false;
    }
}
