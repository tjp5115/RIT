__author__ = 'Tyler Paulsen'
import matplotlib.pyplot as plt
import csv
import math

def scatter_plot(points):
    for point in points:
        if(point[3] == 0 ):
            c = 'b'
        else:
            c = 'r'
        plt.scatter(point[0],point[1],color=c)
        plt.scatter(point[1],point[2],color=c)
        plt.scatter(point[0],point[2],color=c)
    plt.show()

def scan_csv(file):
    list = []
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        next(reader, None)
        for line in reader:
            #make bins of size .5
            list.append([float(i) for i in line])
    return list

def calcImpurity(list,num_case,calc):
    miss_error = float('inf')
    miss_array = [];
    for i in range(num_case):
        miss_array.clear()
        #plt.figure()
        list = sorted(list,key=lambda x: x[i])
        for j in range(len(list)-1):
            eval_miss_error = calc(list,j)
            miss_array.append(eval_miss_error)
            if eval_miss_error < miss_error:
                miss_error = eval_miss_error
                indices = (j,i)
        #plt.plot(range(len(miss_array)),miss_array,'ro')
        #plt.draw()
    print("Lowest Miss Error: ",miss_error)
    return indices


def calcGini(list, i):
    c1 = sum(x[-1] == list[i][-1] for x in list[:i])
    c2 = sum(x[-1] == list[i][-1] for x in list[i+1:])
    c2 = len(list) - i + 1 - c2
    size = len(list)
    split1 = i + 1
    split2 = size - split1
    g1 = 1 - (c1/split1)**2 - ((split1-c1)/split1)**2
    g2 = 1 - (c2/split2)**2 - ((split2-c2)/split2)**2
    return split1/size*g1 + split2/size*g2

def calcMissError(list, i):
    c1 = sum(x[-1] == list[i][-1] for x in list[:i])
    c2 = sum(x[-1] == list[i][-1] for x in list[i+1:])
    # get the number opposite of c1 counted.
    c2 = len(list)-(i+1) - (c1+c2)
    return max([c1,c2]) / len(list)

def calcEntropy(list, i):
    size = len(list)
    c1 = sum(x[-1] == list[i][-1] for x in list[:i])
    c2 = sum(x[-1] == list[i][-1] for x in list[i+1:])
    c2 = size - (i+1) - c2
    split1 = i + 1
    split2 = size - split1
    if c1/split1 == 0 or c2/split2 == 0 or (split2-c2)/split2 == 0 or (split1-c1)/split1 == 0:
        return float('inf')
    e1 = -1*((c1/split1) * math.log(c1/split1,2)) - (split1-c1)/split1 * math.log((split1-c1)/split1,2)
    e2 = -1*((c2/split2) * math.log(c2/split2,2)) - (split2-c2)/split2 * math.log((split2-c2)/split2,2)
    return split1/size*e1 + split2/size*e2

list = scan_csv("HW04_Training_Data__v010.csv")
print("list Length: ",len(list))
#scatter_plot(list)
print("Gini:")
print(calcImpurity(list,3,calcGini))
print("Miscalssification Error:")
print(calcImpurity(list,3,calcMissError))
print("Entropy: ")
print(calcImpurity(list,3,calcEntropy))
#plt.show()
