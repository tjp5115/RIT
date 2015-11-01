__author__ = 'Tyler Paulsen'
import numpy as np

class cluster:
    """
    cluster class keeps track of the COM, id, and cart entries
    """
    def __init__(self,id,cart):
        """
        constructor for class
        :param id: id of cart
        :param cart: cart to add
        """
        self.id = [ id ]
        self.cart = np.array(cart)
        self.COM = self.cart
    def group(self, shopper):
        """
        groups two clusters cluster classes together.
        combines the carts, ids, and compuets the new COM of the combined cart.
        :param shopper:
        """
        self.cart = np.vstack([self.cart,shopper.cart])
        self.id = self.id + shopper.id
        # COM is the mean of each cart entry.
        self.COM = self.cart.mean(axis=0)

def scan_csv(file,col=None):
    """
    scan a csv file and return the results as a numpy array of floating point numbers
    :param file: csv file
    :return: numpy array of floating point numbers
    """
    return np.loadtxt(file, delimiter=',',skiprows=1,usecols=col)


def get_cart(file):
    """
    separates the CSV file into a unique cluster for each entry.
    each cluster is of the class: cluster.
    :param file: CSV file to use for cart clusters
    :return:
    """
    cart = []
    # shows what items to remove. The lowest standard deviation shows what items are normally bought by each buyer.
    # the lowest values will then be entered
    std_items = np.std(scan_csv(f),axis=0)
    print("standard Deviation(to remove item)\n "+ str(std_items))
    remove_item = std_items.tolist().index(min(std_items))
    # makes a new list of all item column indexs
    col_to_keep = list(range(len(std_items)))
    # deletes the col index that had the lowest STD
    del col_to_keep[remove_item]
    # iterate through the CSV list, and create a new cluster object for each entry.
    for id in scan_csv("HW_09_SHOPPING_CART_v037.csv",col_to_keep):
        cart.append(cluster(id[0],id[1:]))
    return cart


def combine_cart(cart):
    """
    combines two clusters in a cart of cluster.
    uses the euclidean distance beteen the Center of masses between two clusers to determine what to combine.
    :param cart: cart to group two clusters together.
    """
    dist = float('inf')
    c,r = 0,0
    for i in range(len(cart)):
        for k in range(len(cart)):
            # skip cart if it is equal to the outer.
            if(cart[i] == cart[k]):
                continue
            # compute the Euclidean distance.
            compDist = np.linalg.norm(cart[i].COM-cart[k].COM)
            if(compDist < dist):
                # update the closest distance
                dist = compDist
                c,r = i,k
    # add the groups to the global variable groups.
    groups.append(cart[c].id+cart[r].id)
    # compute the new COM, add new ids, and add the new cart values
    cart[c].group(cart[r])
    # delete the old cluster from the cart
    del cart[r]

def agglomerativeCluster(cart):
    """
    the main loop for the agglomerative cluster.
    :param cart: cart to cluster
    """
    while(len(cart) != 1 ):
        # helper method to help code be more readable.
        combine_cart(cart)


# global variable groups
# takes a snapshot of each iteration of the cluster.
# used to determine what the largest clusters were.
groups = []
f = "HW_09_SHOPPING_CART_v037.csv"
cart = get_cart(f)
agglomerativeCluster(cart)

# print the cluster snapshots
print("\nIterations of Clustering")
i = 1
for g in groups:
    print(str(i) + " -> "+str(g))
    i += 1

# each c? value was taken after the inital agglomerative clustering algorithm.
# the largest three clusters were copied into the arrays.
c1 = [x - 1 for x in [1.0, 22.0, 4.0, 9.0, 13.0, 12.0, 8.0]]
c2 = [x - 1 for x in [2.0, 32.0, 5.0, 11.0, 19.0, 25.0, 18.0, 31.0, 17.0, 26.0, 14.0, 30.0, 24.0, 27.0, 15.0, 6.0]]
c3 = [x - 1 for x in [3.0, 29.0, 10.0, 21.0, 23.0, 16.0, 28.0, 33.0, 20.0, 7.0]]

# use the clusters c1-c3 to determine what items are bought the most in the group.
carts = scan_csv("HW_09_SHOPPING_CART_v037.csv",)
# shows cluster likes eggs and beer
std_items = np.std(carts[c1],axis=0)
print("standard Deviation(find item)\n "+str(std_items.tolist().index(min(std_items))))
# shows cluster likes veggies and nuts
std_items = np.std(carts[c2],axis=0)
print("standard Deviation(find item)\n "+str(std_items.tolist().index(min(std_items))))
# shows cluster likes meat rice and nuts
std_items = np.std(carts[c3],axis=0)
print("standard Deviation(find item)\n "+str(std_items.tolist().index(min(std_items))))
