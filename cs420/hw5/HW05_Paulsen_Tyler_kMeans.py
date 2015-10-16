__author__ = 'Tyler Paulsen'
import matplotlib.pyplot as plt
import math
import numpy as np
from random import randint


def scan_csv(file):
    """
    scan a csv file and return the results as a numpy array of floating point numbers
    :param file: csv file
    :return: numpy array of floating point numbers
    """
    points = np.loadtxt(file, delimiter=',')
    s = points.shape
    b = np.zeros((s[0],s[1]+1))
    b[:,:-1] = points
    return b

def plot_sse(lst):
    """
    plots the SSE graph. SSE vs K
    :param lst: list to plot
    """
    plt.figure()
    plt.title("SSE Plot")
    plt.xlabel("K")
    plt.ylabel("SSE")
    plt.plot(lst)
    plt.draw()

def scatter_plot(points,centroids,title):
    """

    :param points: points to plot.
    :param centroids: Centroids to the clusters in the points
    :param title: title of the graph
    """

    #create a new figure so there can be multiple graphs
    plt.figure()
    plt.title(title)
    plt.xlabel("x")
    plt.ylabel("y")
    for point in points:
        # give the points some color based on what classification they are.
        x = point[0]
        y = point[1]
        c = point[3]
        if c == 0:
            c = 'b'
        elif c == 1:
            c = 'y'
        elif c == 2:
            c = 'g'
        elif c == 3:
            c = 'c'
        elif c == 4:
            c = 'm'
        else:
            c = 'k'
        plt.scatter(x,y,color=c)

    for point in centroids:
        x = point[0]
        y = point[1]
        plt.scatter(x,y,marker='*',color='red',s=[100])
    plt.draw()

def pre_process(points):
    """
    rotates the points by 45 degrees
    :param points: points to process
    """
    for point in points:
        x = point[0]
        y = point[1]
        a = 45
        point[0] = x*math.cos(a)-y*math.sin(a)
        point[1] = y*math.cos(a) + x*math.sin(a)

def clean_data(points,centroids):
    """
    cleans the data based on centroids. finds the average distance, and uses that with the standard deviation to
    determine if a data point is an outlier.
    :param points: is a nx4 numpy array (x,y,z,centriod_index)
    :param centroids: numpy array of centroids
    :return: points array that was modified.
    """
    avg_dist = []
    for i in range(centroids.shape[0]):
        # get the points with the centroid index of i
        centroid_points = points[np.where(points[:,-1]==i)]
        temp = []
        for point in centroid_points:
            #append the euclidean distance of the points with centroid i to a list.
            temp.append(np.linalg.norm(centroids[i][0:-2]-point[0:-2]))
        # process the temp list, and add the standard deviation and average to a new list.
        avg_dist.append([np.average(temp),np.std(temp)])

    delete = []
    # delete points if outliers
    for i in range(points.shape[0]):
        k = int(points[i][-1])
        # calculate the euclidean distance
        dist = np.linalg.norm(centroids[k][0:-2]-points[i][0:-2])
        # if the distance is > the average + standard deviation, remove it.
        if dist > avg_dist[k][0]+avg_dist[k][1]:
            # append the index to an array
            delete.append(i)
    # remove all indexes in the delte array
    points = np.delete(points,delete,0)
    return points

def point_to_centroid(p,c):
    """
    links a point to the closest centroid
    :param p: points to process is a nx4 numpy array (x,y,z,centroid_index)
    :param c: centroids to link to points
    :return: new point list.
    """
    for i in p:
        min_dist = float('inf')
        min_centroid = None
        for k in range(c.shape[0]):
            # get the euclidean distance
            compute_dist = np.linalg.norm(i[0:-2]-c[k][0:-2])
            if compute_dist < min_dist:
                min_dist = compute_dist
                min_centroid = k
        i[-1] = min_centroid
    return p


def compute_new_centroid(points, centroids):

    """
    uses the points to create a new list of centroids. uses the mean between all points in a given cluster for
    new x, y, or z value
    :param points: nx4 numpy array (x,y,z,centriod_index)
    :param centroids: old centroids
    :return: list of centroids
    """
    try:
        for i in range(centroids.shape[0]):
            # get the points with the centroid index of i
            centroid_points = points[np.where(points[:,-1]==i)]
            # get the mean for each column
            x = np.mean(centroid_points[:,0])
            y = np.mean(centroid_points[:,1])
            z = np.mean(centroid_points[:,2])
            # replace the old centroid value with the new one.
            centroids[i] = [x,y,z,0]
    except RuntimeWarning:
        print(centroid_points)
    return centroids

def kmeans(points, k):
    """

    :param points: nx4 numpy array (x,y,z,centriod_index)
    :param k: number of klusters to create
    :return: a list of centroids
    """
    centroids = []
    size = len(points)
    # initialize centroid array with values from points
    for i in range(k):
        centroids.append(points[randint(0,size-1)])
    # convert to numpy array
    centroids = np.array(centroids)
    old_centroids = None
    # limit on the number of iterations.
    i = 0
    while i < 1000 and not np.array_equal(centroids,old_centroids):
        i += 1
        # copy the current centroid into the old one.
        old_centroids = np.copy(centroids)
        # label the points with its what clusters they are in

        labeled_points = point_to_centroid(points,centroids)
        # compute the new centroid points
        centroids = compute_new_centroid(labeled_points, centroids)
    return centroids


def SSE(points,centroids):
    """

    :param points: nx4 numpy array (x,y,z,centriod_index)
    :param centroids: centroids to calculate with points
    :return: the SSE
    """
    sum = 0
    for i in range(centroids.shape[0]):
        centroid_points = points[np.where(points[:,-1]==i)]
        for point in centroid_points:
            # add up all the euclidean distances squared
            sum += np.linalg.norm(centroids[i][0:-2]-point[0:-2])**2
    return sum


def print_cluster_size(points,centroids):
    """
    prints a sorted list of the sizes of each centroid
    :param points: nx4 numpy array (x,y,z,centriod_index)
    :param centroids: used for the size of the numpy array
    """
    size = []
    for i in range(centroids.shape[0]):
        size.append(points[np.where(points[:,-1]==i)].shape[0])
    print(sorted(size))


lst = scan_csv('HW_KMEANS_DATA_v015.csv')
pre_process(lst)
sse_lst = []
print("*** K-Means to plot SSE vs K ***")

# used to find clusters 1 - 10 with their SSE values.
# saved and graphed at the end
for k in range(1,10):
    sse_min = float('inf')
    centroids = kmeans(lst,k)
    sse = SSE(lst,centroids)
    if sse < sse_min:
        sse_min = sse
        min_centroids = centroids
    print("Min-SSE=",sse_min)
    print("iteration=",k)
    sse_lst.append(sse_min)
plot_sse(sse_lst)

sse_min = float('inf')
print()
print("*** K-Means to estimate cluster points ***")
print("*** Used to get points for pre "
      "# loop 5 times to get a closer to actual valueprocessing ***")
print()
centroids = kmeans(lst,5)
sse = SSE(lst,centroids)
if sse < sse_min:
    sse_min = sse
    min_centroids = centroids
    min_lst = np.copy(lst)
lst = clean_data(min_lst,min_centroids)
scatter_plot(lst,min_centroids,"First Run of k-means")
print()
print("*** K-Means after Pre-Processing ***")
print()
centroids = kmeans(lst,5)
sse = SSE(lst,centroids)
if sse < sse_min:
    sse_min = sse
    min_centroids = centroids
    min_lst = np.copy(lst)
min_lst = clean_data(min_lst,min_centroids)
print("Min-SSE=",sse_min)
scatter_plot(min_lst,min_centroids,"Second of K-Means After Pre-Processing.")
print_cluster_size(min_lst,min_centroids)
plt.show()

