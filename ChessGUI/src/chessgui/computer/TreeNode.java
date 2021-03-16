package chessgui.computer;

import java.util.*;


public class TreeNode {
    protected TreeNode parent;
    protected ArrayList<TreeNode> children;
    protected int depth;
    public int data;
    
    public TreeNode(TreeNode parent, int data) {
        this.parent = parent;
        this.children = new ArrayList();
        this.data = data;
        if (parent != null) {
            this.parent.addChild(this);
            this.depth = this.parent.depth + 1;
        }
        else {
            this.depth = 0;
        }
    }
    
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }
    
    public void addChild(TreeNode child) {
        this.children.add(child);
    }
    
    public void printTree() {
        System.out.println(this);
        int depth = 1;
        if (this.children != null) {
            for (TreeNode nodeChild : this.children) {
                nodeChild.printTreeBranch();
            }
        }
    }
    
    public void printTreeBranch() {
        System.out.println(this);
        if (this.children != null) {
            for (TreeNode nodeChild : this.children) {
                nodeChild.printTreeBranch();
            }
        }
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.data);
        buffer.append('\n');
        for (Iterator<TreeNode> it = children.iterator(); it.hasNext();) {
            TreeNode next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}