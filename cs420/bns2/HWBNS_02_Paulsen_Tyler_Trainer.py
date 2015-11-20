import csv
import math
import operator
import copy

__author__ = 'Tyler Paulsen'

class Node(object):
    def __init__(self, data=None):
        self.data = data
        self.children = []

    def add_data(self,data):
        self.data = data

    def add_child(self, obj):
        self.children.append(obj)

def scan_csv(file):
    """
    scan a csv file and return the results as a multidimensional array of floating point numbers
    :param file: csv file
    :return: multidimentional array of floating point numbers
    """
    dec_list = []
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        # skip the first line
        next(reader, None)
        for line in reader:
            dec_list.append([float(i) for i in line])
    return dec_list

def calcImpurity(dec_list,num_case,calc,type=None,count=None,tree=None):
    """
    calculates the impurity of a 4 element multi-dim list, and prints out a tree with a root and two leafs.
    :param dec_list: list to calculate
    :param num_case: number of elements left to calc in list
    :param calc:  function to use for calculations. must take a list and an index.
    :param type: Only expecting a depth of 2 for the recursion. The left and the right node to a tree.
                    this is a string to indication the left of the right node
    :param count: a recursion depth stopper. Send it to the function if you dont want it to each a certain depth
    :param tree: decision tree.

    """
    miss_array = [];
    # if an array is not given for the number of cases, turn it into one. used to remove the case that has
    # the lowest impurity for that node
    if not isinstance(num_case, list):
        num_case = list(range(num_case))
    # base case
    if count == 0 or len(dec_list) == 0:
        return
    for i in num_case:
        miss_error = [float('inf'),0]
        # sort the list on the current attribute being calculated
        dec_list = sorted(dec_list,key=lambda x: x[i])
        # find the lowest impurity
        for j in range(len(dec_list)-1):
            # use the function passed in a parameter.
            eval_miss_error = calc(dec_list,j)
            # keep the lowest error rate
            if eval_miss_error < miss_error[0]:
                miss_error = [eval_miss_error,j]
        # add the lowest impurity to an array
        miss_array.append([i]+miss_error)
    # get the lowest impurity. Used for debuging to ensure all calculations were happening.
    min_val = float('inf')
    r = None;
    # get the lowest calculation (entropy, misscalculation, or gini)
    for v in miss_array:
        if v[1] < min_val:
            # what index to split on
            r = v[2]
            # the attribute that was split on
            c = v[0]
            min_val = v[1]
    # this is the stopper to know if we can not go into another recusive step.
    # the array is empty, and no value was found as the lowest
    if r == None:
        return
    size = []
    # sort the list based on the lowest index to get the correct value
    dec_list = sorted(dec_list,key=lambda x: x[num_case[c]])
    index = num_case[c]
    if tree == None:
        # init the tree ( 1st iteration )
        tree = Node((dec_list[r][c],c))
    else:
        # add a new node to the tree
        tree.add_data((dec_list[r][c],c))
    # add two new children to the new parent node
    tree.add_child(Node())
    tree.add_child(Node())

    # decrement optional counter
    if count != None:
        count -= 1

    # calculate each side of the node
    calcImpurity(dec_list[:r],num_case,calc,"left",count,tree.children[0])
    calcImpurity(dec_list[r+1:],num_case,calc,"Right",count,tree.children[1])
    return tree;

def calcGini(dec_list, i):
    """
    calculation for the Weighted Gini index
    :param dec_list: list to perform calculation
    :param i: index to split the list at
    :return: float - the weighted Gini index calculated
    """
    # c? variables are the counts of the index(i) classification on each side of the list
    c1 = sum(x[-1] == dec_list[i][-1] for x in dec_list[:i])
    c2 = sum(x[-1] == dec_list[i][-1] for x in dec_list[i+1:])
    c2 = len(dec_list) - i + 1 - c2
    size = len(dec_list)
    # size of each split
    split1 = i + 1
    split2 = size - split1
    # Gini of each split
    g1 = 1 - (c1/split1)**2 - ((split1-c1)/split1)**2
    g2 = 1 - (c2/split2)**2 - ((split2-c2)/split2)**2
    # weighted Gini
    return split1/size*g1 + split2/size*g2

def calcMisError(dec_list, i):
    """
    calculation for the misclassification Error
    :param dec_list: list to perform calculation
    :param i: index to split the list at
    :return: float - the misclassification error calculated
    """
    # c? variables are the counts of the index(i) classification on each side of the list
    c1 = sum(x[-1] == dec_list[i][-1] for x in dec_list[:i])
    c2 = sum(x[-1] == dec_list[i][-1] for x in dec_list[i+1:])
    # get the number opposite of c1 counted.
    c2 = len(dec_list)-(i+1) - (c1+c2)
    return max([c1,c2]) / len(dec_list)

def calcEntropy(dec_list, i):
    """
    calculation for the Weighted Entropy
    :param dec_list: list to perform calculation
    :param i: index to split the list at
    :return: float - the Weighted Entropy calculated
    """
    size = len(dec_list)
    # c? variables are the counts of the index(i) classification on each side of the list
    c1 = sum(x[-1] == dec_list[i][-1] for x in dec_list[:i])
    c2 = sum(x[-1] == dec_list[i][-1] for x in dec_list[i+1:])
    c2 = size - (i+1) - c2
    # size of each side of the split
    split1 = i + 1
    split2 = size - split1
    # make sure we are not taking the log of 0
    if c1/split1 == 0 or c2/split2 == 0 or (split2-c2)/split2 == 0 or (split1-c1)/split1 == 0:
        return float('inf')
    # calculate the entropy of each size
    e1 = -1*((c1/split1) * math.log(c1/split1,2)) - (split1-c1)/split1 * math.log((split1-c1)/split1,2)
    e2 = -1*((c2/split2) * math.log(c2/split2,2)) - (split2-c2)/split2 * math.log((split2-c2)/split2,2)
    return split1/size*e1 + split2/size*e2

dec_list = scan_csv("HWBNS_02_Training__v027_bonus.csv")

print("Gini:")
test =calcImpurity(dec_list,2,calcGini,count=100)
print("Root->",test.data)
print("Root->Left->",test.children[0].data)
print("Root->Right->",test.children[1].data)
print("Root->Right->Left->",test.children[1].children[0].data)
print("Root->Right->Right",test.children[1].children[1].data)
print("Root->Left->Left",test.children[0].children[0].data)
print("Root->Left->Right",test.children[0].children[1].data)

print("\nMiscalssification Error:")
test = calcImpurity(dec_list,2,calcMisError,count=4)
print("Root->",test.data)
print("Root->Left->",test.children[0].data)
print("Root->Right->",test.children[1].data)
print("Root->Right->Left->",test.children[1].children[0].data)
print("Root->Right->Right",test.children[1].children[1].data)
print("Root->Left->Left",test.children[0].children[0].data)
print("Root->Left->Right",test.children[0].children[1].data)


print("\nEntropy: ")
test = calcImpurity(dec_list,2,calcEntropy,count=4)
print("Root->",test.data)
print("Root->Left->",test.children[0].data)
print("Root->Right->",test.children[1].data)
print("Root->Right->Left->",test.children[1].children[0].data)
print("Root->Right->Right",test.children[1].children[1].data)
print("Root->Left->Left",test.children[0].children[0].data)
print("Root->Left->Right",test.children[0].children[1].data)


