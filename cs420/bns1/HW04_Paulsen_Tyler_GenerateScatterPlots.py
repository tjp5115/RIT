import csv
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

__author__ = 'Tyler Paulsen'


def scatter_plot(points):
    """

    :param points: points to plot. expecting the csv list from HW04 data
    """
    for point in points:
        # give the points some color based on what classification they are.
        if(point[2] == 0 ):
            c = 'b'
        else:
            c = 'r'
        plt.scatter(point[0],point[1],color=c)
    red_patch = mpatches.Patch(color='red', label='1')
    blue_patch = mpatches.Patch(color='blue', label='0')
    plt.legend(handles=[red_patch,blue_patch])
    plt.title("Attributes combinations (1vs2)")
    plt.xlabel("Attribute 0")
    plt.ylabel("Attribute 1")
    plt.show()

def scan_csv(file):
    """
    scan a csv file and return the results as a multidimensional array of floating point numbers
    :param file: csv file
    :return: multidimentional array of floating point numbers
    """
    dec_list = []
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        # skip the header line
        next(reader, None)
        for line in reader:
            dec_list.append([float(i) for i in line])
    return dec_list

dec_list = scan_csv("HWBNS_01_Training__v026_bonus.csv")
scatter_plot(dec_list)
