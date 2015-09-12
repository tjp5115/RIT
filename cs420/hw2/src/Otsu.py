import statistics
import math
import csv
__author__ = 'Tyler'


def otsu(thresholds):
    """

    :param thresholds: Bins to find the best two clusters
    :return best_missed_var, best_threshold: The best variance and the index of it.
    """
    best_mixed_var = None
    best_threshold = None
    total = len(thresholds)
    for midpoint in range(2, total-2):
        wt_under = statistics.mean(thresholds[:midpoint])
        var_under = statistics.variance(thresholds[:midpoint])
        wt_over = statistics.mean(thresholds[midpoint+1:])
        var_over = statistics.variance(thresholds[midpoint+1:])
        mixed_var = wt_under * var_under + wt_over * var_over
        #print("index %s's mixed_var= %s"% (midpoint, mixed_var))
        #print("wt_over= ", wt_over)
        #print("wt_under= ", wt_under)
        #print("var_over= ", var_over)
        #print("var_under= ",var_under)
        if mixed_var == 0:
            continue
        print(mixed_var)
        if best_mixed_var is None:
            best_mixed_var = mixed_var
            best_threshold = midpoint
        if mixed_var < best_mixed_var:
            best_mixed_var = mixed_var
            best_threshold = midpoint
    return best_mixed_var, best_threshold


def get_bin_index(index_speed, index_min_speed, index_bin_size):
    """
    Finds the index for the speed.
    :param index_speed: speed to index
    :param index_min_speed: min speed for the bin
    :param index_bin_size: size of the bin
    :return: index of the bin
    """
    return math.floor((index_speed-index_min_speed)/index_bin_size)


def get_bin_speed(index_speed, index_min_speed, index_bin_size):
    """

    :param index_speed: index to
    :param index_min_speed: min speed for the bin
    :param index_bin_size: size of the bin
    :return:
    """
    min_speed = index_speed*2+index_min_speed
    return str(min_speed) + "-" +str(min_speed+2)


def create_bins(file, min_speed, max_speed, bin_size):
    # init the array for the bins
    max_bin = int(((max_speed-min_speed)/bin_size))
    bins = [0] * max_bin

    # question
    # split the data into bins of size 2 in a range from 38-80
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        for line in reader:
            speed = float(line[0])
            # get the bin for the speed
            index = get_bin_index(speed, min_speed, bin_size)
            #print("index: %s - speed: %s" % (index, speed))
            if index < 0 or index > max_bin:
                continue
            else:
                bins[index] += 1
    return bins

unclassified_bins = create_bins("../UNCLASSIFIED_Speed_Observations_for_128_vehicles.csv", 38, 80, 2)
print(unclassified_bins)
var, index = otsu(unclassified_bins)
print("lowest mixed var: ", var)
print("bin ( %s ) index: %s" % (get_bin_speed(index, 38, 2), index))
print("bin[index] =", unclassified_bins[index])
print()

mystery_bins = create_bins("../Mystery_Data.csv", 6, 40, 2)
print(mystery_bins)
var, index = otsu(mystery_bins)
print("lowest mixed var: ", var)
print("bin ( %s ) index: %s" % (get_bin_speed(index, 6, 2), index))
print("bin[index] =", mystery_bins[index])
print(math.floor((6-6)/3))


