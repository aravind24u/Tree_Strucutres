package com.aravind.ds.tree.bst;

public class TreeNode {

	private Integer value;
	private TreeNode leftNode;
	private TreeNode rightNode;

	public TreeNode() {
		setValue(null);
		setLeftNode(null);
		setRightNode(null);
	}

	public TreeNode(Integer value) {
		super();
		setValue(value);
		setLeftNode(null);
		setRightNode(null);
	}

	public TreeNode(Integer value, TreeNode leftNode, TreeNode rightNode) {
		super();
		setValue(value);
		setLeftNode(leftNode);
		setRightNode(rightNode);
	}

	public Boolean addNode(Integer value) {

		Boolean isAdded = Boolean.FALSE;

		if (getValue() == value) {
			isAdded = Boolean.FALSE;
			System.out.println("Trying to Add a Duplicate Value");
		} else {

			TreeNode nextElement = null;

			if (getValue() < value) {
				nextElement = getRightNode();
			} else {
				nextElement = getLeftNode();
			}
			if (nextElement != null) {
				isAdded = nextElement.addNode(value);
			} else {
				TreeNode newElement = new TreeNode(value);
				if (getValue() < value) {
					setRightNode(newElement);
				} else {
					setLeftNode(newElement);
				}
			}
		}
		return isAdded;
	}

	public void printAll() {
		System.out.println(getValue());
		if (getLeftNode() != null) {
			// System.out.print("Left : ");
			getLeftNode().printAll();
		} else {
			// System.out.println("End");
		}
		if (getRightNode() != null) {
			// System.out.print("Right : ");
			getRightNode().printAll();
		} else {
			// System.out.println("End");
		}
	}

	public Boolean searchValue(Integer SearchValue) {

		Boolean isPresent = Boolean.FALSE;

		if (SearchValue == getValue()) {
			isPresent = Boolean.TRUE;
		} else {
			if (getValue() < SearchValue) {
				if (getRightNode() != null) {
					isPresent = getRightNode().searchValue(SearchValue);
				} else {
					isPresent = Boolean.FALSE;
				}
			} else {
				if (getLeftNode() != null) {
					isPresent = getLeftNode().searchValue(SearchValue);
				} else {
					isPresent = Boolean.FALSE;
				}
			}
		}
		return isPresent;
	}

	public Integer getSum() {
		Integer sum = getValue();

		if (getRightNode() != null) {
			sum += getRightNode().getSum();
		}
		if (getLeftNode() != null) {
			sum += getLeftNode().getSum();
		}
		return sum;
	}

	public Integer getMin() {
		Integer min = getValue();
		if (getLeftNode() != null) {
			min = getLeftNode().getMin();
		}
		return min;
	}

	public Boolean deleteNode(Integer value, TreeNode parent) {

		Boolean isDeleted = Boolean.FALSE;

		if (getValue() == value) {
			if (getLeftNode() == null || getRightNode() == null) {
				if (parent != null) {
					if (getRightNode() == null && getLeftNode() == null) {
						if (parent.getRightNode().getValue() == value) {
							parent.setRightNode(null);
						} else {
							parent.setLeftNode(null);
						}
					} else if (getLeftNode() == null) {
						if (parent.getRightNode().getValue() == value) {
							parent.setRightNode(getRightNode());
						} else {
							parent.setLeftNode(getRightNode());
						}
					} else {
						if (parent.getRightNode().getValue() == value) {
							parent.setRightNode(getLeftNode());
						} else {
							parent.setLeftNode(getLeftNode());
						}
					}
				} else {
					if (getRightNode() == null && getLeftNode() == null) {
						this.setValue(null);
					} else if (getLeftNode() == null) {
						setRightNode(getRightNode().getRightNode());
						setLeftNode(getRightNode().getLeftNode());
						setValue(getRightNode().getValue());
					} else {
						setRightNode(getLeftNode().getRightNode());
						setLeftNode(getLeftNode().getLeftNode());
						setValue(getLeftNode().getValue());
					}
				}

			} else {
				Integer rightMin = getRightNode().getMin();
				setValue(rightMin);
				getRightNode().deleteNode(rightMin, this);
			}
			isDeleted = Boolean.TRUE;
		} else if (value > getValue()) {
			isDeleted = getRightNode().deleteNode(value, this);
		} else {
			isDeleted = getLeftNode().deleteNode(value, this);
		}

		return isDeleted;
	}

	public Integer getSize() {
		int size = 1;

		if (getRightNode() != null) {
			size += getRightNode().getSize();
		}
		if (getLeftNode() != null) {
			size += getLeftNode().getSize();
		}
		return size;
	}

	public Integer getHeight() {

		Integer height = 1;
		Integer leftHeight = 0;
		Integer rightHeight = 0;

		if (getRightNode() != null) {
			rightHeight = getRightNode().getHeight();
		}
		if (getLeftNode() != null) {
			leftHeight = getLeftNode().getHeight();
		}
		if (leftHeight == rightHeight || leftHeight > rightHeight) {
			height += leftHeight;
		} else {
			height += rightHeight;
		}
		return height;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public TreeNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(TreeNode leftNode) {
		this.leftNode = leftNode;
	}

	public TreeNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(TreeNode rightNode) {
		this.rightNode = rightNode;
	}
}
