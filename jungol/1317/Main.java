import java.util.Scanner;

public class Main {
    static int n;

    static int relationship[][];
    static int assignedMembers[];

    static int numOfClusterMembers;
    static int clusterMembers[];

    static int numOfConnections[];
    static int connections[][];

    static int numOfClusters;
    static int clusterLeaders[];

    private static void solve() {
        for (int i = 0; i < n; i++) {
            if (assignedMembers[i] == 1) {
                continue;
            }

            findCluster(i);
            clusterLeaders[numOfClusters++] = electLeader();
        }
    }

    private static void findCluster(int startMember) {
        numOfClusterMembers = 0;
        clusterMembers[numOfClusterMembers++] = startMember;
        assignedMembers[startMember] = 1;
        for (int i = 0; i < numOfClusterMembers; i++) {
            int id = clusterMembers[i];
            for (int j = 0; j < n; j++) {
                if (assignedMembers[j] == 0 && relationship[id][j] == 1) {
                    clusterMembers[numOfClusterMembers++] = j;
                    assignedMembers[j] = 1;
                }
            }
        }
    }

    private static int electLeader() {
        if (numOfClusterMembers == 1) {
            return clusterMembers[0];
        }

        for (int i = 0; i < numOfClusterMembers; i++) {
            int id = clusterMembers[i];
            connections[id][id] = 0;
            numOfConnections[id]++;
        }

        for (int i = 0; i < numOfClusterMembers; i++) {
            for (int j = 0; j < numOfClusterMembers; j++) {
                int id = clusterMembers[j];
                for (int k = 0; k < numOfClusterMembers; k++) {
                    int connectedId = clusterMembers[k];
                    if (connections[id][connectedId] != i) {
                        continue;
                    }

                    for (int l = 0; l < numOfClusterMembers; l++) {
                        int nextConnectedId = clusterMembers[l];
                        if (connections[id][nextConnectedId] < 0 && relationship[connectedId][nextConnectedId] > 0) {
                            connections[id][nextConnectedId] = i + 1;
                            if (numOfClusterMembers <= ++numOfConnections[id]) {
                                return id;
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();

        relationship = new int[n][n];
        assignedMembers = new int[n];
        clusterMembers = new int[n];
        numOfConnections = new int[n];
        connections = new int[n][n];
        clusterLeaders = new int[n];

        int r = in.nextInt();
        for (int i = 0; i < r; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            relationship[x - 1][y - 1] = 1;
            relationship[y - 1][x - 1] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                connections[i][j] = -1;
            }
        }

        solve();

        for (int i = 0; i < numOfClusters; i++) {
            for (int j = numOfClusters - 1; j > i; j--) {
                if (clusterLeaders[j] < clusterLeaders[j - 1]) {
                    int temp = clusterLeaders[j];
                    clusterLeaders[j] = clusterLeaders[j - 1];
                    clusterLeaders[j - 1] = temp;
                }
            }
        }

        System.out.println(numOfClusters);
        for (int i = 0; i < numOfClusters; i++) {
            System.out.println(clusterLeaders[i] + 1);
        }
    }
}