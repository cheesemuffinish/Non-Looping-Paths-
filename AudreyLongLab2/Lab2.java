import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.io.IOException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
public class Lab2 {

//------------------------------------------------------------------------------
// method to find all possible paths from selected node
// the inputs include an adjancy matrix, a filepath, a boolen to see the node 
// has been visited, start node, end node, parent node to keep track of where 
// we have been,a endOfNodePath boolean to find the last node and a string buffer 
// to keep track of the paths visited.
//------------------------------------------------------------------------------

      static void findPath(int[][] adjMatrix, int[] parentNode, boolean[] nodeVisited, int startNode, int endNode, int firstNode, int lastNode,
            boolean endOfNodePath, StringBuffer pathTraveled) {
            if ((firstNode >= adjMatrix.length) || (lastNode >= adjMatrix.length))

                  return;

            // Check nodal ranges 
            if (endOfNodePath) 
            {
                  pathTraveled.append((startNode + 1));
                  getPath(parentNode, startNode, parentNode[endNode], pathTraveled);
                  pathTraveled.append(" to " + (endNode + 1) + System.lineSeparator());
                  return;
            }

            // Check path from firstNode to lastNode

            if ((adjMatrix[firstNode][lastNode] != 0) && (!nodeVisited[lastNode])) 
            {
                  nodeVisited[lastNode] = true;
                  parentNode[lastNode] = firstNode;
                  if (lastNode == endNode)
                  {      
                        endOfNodePath = true;
                  }      
                  else
                  {
                        endOfNodePath = false;
                  }

                  findPath(adjMatrix, parentNode, nodeVisited, startNode, endNode,
                  lastNode, 0, endOfNodePath, pathTraveled);
                  nodeVisited[lastNode] = false;
                  parentNode[lastNode] = -1;
                  endOfNodePath = false;
            }

            //Trace the path from the selected node
            findPath(adjMatrix, parentNode, nodeVisited, startNode, endNode, firstNode, lastNode + 1, endOfNodePath, pathTraveled);
      }

//------------------------------------------------------------------------------
// method to write possible paths to an adjacency matrix
// the inputs include an output file to write the result, 
// an adjancy matrix, a filepath 
//------------------------------------------------------------------------------

      static void writeadjMatrix(String outputFile, int[][] adjMatrix, String filePath, boolean append) throws IOException {
            FileWriter writer = new FileWriter(new File(outputFile), append);
            for (int i = 0; i < adjMatrix.length; i++) 
            {
                  for (int j = 0; j < adjMatrix.length; j++) 
                  {
                        writer.write(adjMatrix[i][j] + " ");
                  }
                  writer.append(System.lineSeparator());
            }
            writer.write(filePath + "" + System.lineSeparator());
            writer.write("~~~~~~~~~~~Reading from the next node~~~~~~~~~~~~" + System.lineSeparator());
            writer.close();
      }



//------------------------------------------------------------------------------
// method to display possible paths via node
// the inputs include an adjancy matrix
// outputs a string of path possibilities
//------------------------------------------------------------------------------

      static String allPossiblePaths(int[][] adjMatrix) {

            int matrixLength = adjMatrix.length;
            StringBuffer possiblePath = new StringBuffer();
            for (int i = 0; i < matrixLength; i++) 
            {
                  for (int j = 0; j < matrixLength; j++) 
                  {
                        boolean[] nodesVisited = new boolean[matrixLength];
                        int[] parentNode = new int[matrixLength];
                        //append to the list of nodes visited
                        if (i != j)
                        {
                              nodesVisited[i] = true;
                        }
                        possiblePath.append(System.lineSeparator() + "Possible paths from " + (i + 1) + " : " + (j + 1) + System.lineSeparator());
                        StringBuffer paths = new StringBuffer();
                        findPath(adjMatrix, parentNode, nodesVisited, i, j, i, 0, false, paths);
                        if (paths.toString().equals(""))
                              paths.append("\tNo Path Found" + System.lineSeparator());
                        possiblePath.append(paths);
                  }
            }
            return possiblePath.toString();

      }

//------------------------------------------------------------------------------
// method to return the path from source 
// the inputs include a parent array, includes integers source and i,
// a string buffer of allpossible paths 
//------------------------------------------------------------------------------


      private static void getPath(int[] parentNode, int initialNode, int i, StringBuffer allPossiblePaths) {

            if (i == initialNode)

                  return;

            else {

                  getPath(parentNode, initialNode, parentNode[i], allPossiblePaths);

                  allPossiblePaths.append(" to " + (i + 1));

            }

      }

//------------------------------------------------------------------------------
// method to display data in output file
// the inputs includes the input file
// the output returns an output file of data
//------------------------------------------------------------------------------
      static void readAdjacMatrix(String inputFile, String outFile) throws IOException {

            Scanner file = new Scanner(new File(inputFile));
            boolean adjacent = true;
            int[][] adjMatrix = new int[0][0];

            while (file.hasNextLine()) {
                  int inFile = file.nextInt();
                  adjMatrix = new int[inFile][inFile];
                  for (int i = 0; i < inFile; i++)
                   {
                        for (int j = 0; j < inFile; j++)
                        {
                                  adjMatrix[i][j] = file.nextInt();
                        }

                  }

                  String edge = allPossiblePaths(adjMatrix);
                  if (adjacent)
                  {
                        writeadjMatrix(outFile, adjMatrix, edge, false);
                        adjacent = false;
                  } 
                  else
                  {
                        writeadjMatrix(outFile, adjMatrix, edge, true);
                  }
            }
            file.close();

      } 

      public static void main(String[] input1) {
            if (input1.length == 2) {
                  String inFile = input1[0];
                  String outputFile = input1[1];
                  try {
                        readAdjacMatrix(inFile, outputFile);
                      
                  } catch (FileNotFoundException fnfe) {

                        System.out.println("The file " + inFile + " was not found.");

                  } catch (IOException ioe) {

                        System.out.println("There was an IOE exception! " + ioe);

                  }
                  catch (Exception exception) 
                  {
                        System.out.println("There was an Exception! ");
                  }
                        

            } else

                  System.out.println("Enter an input and output file!");

            }

}