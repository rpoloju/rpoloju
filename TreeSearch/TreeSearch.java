import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class TreeSearch {

	private static Node tree;
	private static TreeNode treeNode;
	private static LeafNode leafNode;
	private static int degree;

	// The first line of the input file must be degree.
	// Create a leaf initially
	private TreeSearch(int order) {
		degree = order;
		tree = new LeafNode(degree);
	}

	private static void insertIntoTree(DataNode dnode) {
		tree = tree.insert(dnode);
	}

	public static void main(String[] args) throws IOException {

		FileInputStream in = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			in = new FileInputStream(args[0]);
			fw = new FileWriter("output_file.txt");
			bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// read the order of the tree
			int treeOrder = Integer.parseInt(br.readLine().trim());

			// create a new BPlusTree of the specified order
			new TreeSearch(treeOrder);
			CustomMap<Float, String> keyValues = new CustomMap<>();

			String strLine, newNodeKeyValue, newNodeKey;
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith("Insert")) {
					newNodeKeyValue = strLine.substring(7);

					// removing the last character ")"
					newNodeKeyValue = newNodeKeyValue.substring(0, newNodeKeyValue.length() - 1);

					// splits into key(float) and value(String)
					String newNodeSplit[] = newNodeKeyValue.split(",");
					float key = Float.parseFloat(newNodeSplit[0]);
					// t = new TreeObject(key, newNodeSplit[1]);

					if (newNodeSplit[1].equals("Value1.2")) {
						System.out.println("ttt");
					}
					if (keyValues.get(key) != null) {
						keyValues.put(key, keyValues.get(key) + ", " + newNodeSplit[1]);
					} else {
						insertIntoTree(new DataNode(key));
						keyValues.put(key, newNodeSplit[1]);

					}

				} else if (strLine.startsWith("Search")) {
					newNodeKey = strLine.substring(7);
					// removing the last character ")"
					newNodeKey = newNodeKey.substring(0, newNodeKey.length() - 1);

					String keySplit[] = newNodeKey.split(",");
					if (keySplit.length == 1) {
						boolean isSearchItemFound = tree.search(new DataNode(Float.parseFloat(newNodeKey)));

						if (isSearchItemFound) {
							bw.write(keyValues.get(Float.parseFloat(newNodeKey)));
						} else {
							bw.write("Null");
						}
						bw.write("\n");
					} else {
						Float startKey = Float.parseFloat(keySplit[0]);
						Float endKey = Float.parseFloat(keySplit[1]);

						if (tree.isLeafNode()) {
							for (int i = 0; i <= tree.data.size(); i++) {
								float tempData = ((DataNode) tree.data.elementAt(i)).getData();
								if (tempData >= startKey && tempData <= endKey) {
									bw.write("(" + tempData + "," + keyValues.get(tempData) + ")");
								}
							}
						} else {
							treeNode = (TreeNode) tree;
							int i = 0;
							boolean xptrfound = false;
							boolean maxValueReached = false;
							while (!xptrfound && i < treeNode.data.size() && !maxValueReached) {
								if (((DataNode) treeNode.data.elementAt(i)).inOrder(new DataNode(startKey))) {
									xptrfound = true;
								} else {
									i++;
								}

								if (treeNode.getClass().getName().trim().equals("TreeNode")
										&& !treeNode.getPointerAt(0).getClass().getName().trim().equals("LeafNode")) {
									treeNode = (TreeNode) treeNode.getPointerAt(i);
									xptrfound = false;
								} else {
									leafNode = (LeafNode) treeNode.getPointerAt(0);

									while (!maxValueReached) {
										for (int j = 0; j < leafNode.data.size(); j++) {
											float dataAtJ = leafNode.getDataAt(j).getData();
											if (dataAtJ >= startKey && dataAtJ <= endKey) {
												bw.write("(" + dataAtJ + "," + keyValues.get(dataAtJ) + ")");
											}
											if (dataAtJ >= endKey) {
												maxValueReached = true;
											}
										}
										if (leafNode.getNextNode() != null) {
											leafNode = leafNode.getNextNode();
										}
									}
									bw.write("\n");
								}
							}
						}
					}
				}
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// close all the pointers
		finally {
			if (in != null) {
				in.close();
			}
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
	}
}
