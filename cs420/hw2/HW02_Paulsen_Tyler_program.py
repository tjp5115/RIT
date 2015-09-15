import statistics
import math
import csv
__author__ = 'Tyler'


def otsu(thresholds):
    """
    :param thresholds: Bins to find the best two clusters
    :return best_missed_var, best_threshold: The best variance and the index of the thresholds.
    """
    best_mixed_var = None
    best_threshold = None
    total = len(thresholds)
    total_points = sum(thresholds)
    for midpoint in range(2, total-2):

        # variance and wt for the midpoint, and everything below it.
        wt_under = sum(thresholds[:midpoint])/total_points
        var_under = statistics.variance(thresholds[:midpoint])

        # variance and wt for everything above the midpoint.
        wt_over = sum(thresholds[midpoint+1:])/total_points
        var_over = statistics.variance(thresholds[midpoint+1:])
        '''
        print("index=",midpoint)
        print("var_under",var_under)
        print("wt_under=",wt_under)
        print("wt_over=",wt_over)
            print("var_over=",var_over)
        '''
        # calculate the mixed variance.
        mixed_var = wt_under * var_under + wt_over * var_over
        print(mixed_var)
        # if the mixed variance is none, it is the first time the loop was run, set the variance to the calculated
        # mixed variance.
        if best_mixed_var is None:
            best_mixed_var = mixed_var
            best_threshold = midpoint

        # sets the lowest mixed variance with its index.
        if mixed_var < best_mixed_var:
            best_mixed_var = mixed_var
            best_threshold = midpoint

    # when the looping is complete, return the lowest mixed variance.
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
    returns the speed that the bin represents.
    :param index_speed: index to
    :param index_min_speed: min speed for the bin
    :param index_bin_size: size of the bin
    :return: the speeds that the bins represent.
    """
    min_speed = index_speed*2+index_min_speed
    return str(min_speed) + "-" +str(min_speed+2)


def create_bins(file, min_speed, max_speed, bin_size):
    """
    :param file: file to create the bins from
    :param min_speed: min speed of the bins
    :param max_speed: max speed of the bins
    :param bin_size: size of the bins
    :return: a list of the bins with the quantized data.
    """
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

file1 = "UNCLASSIFIED_Speed_Observations_for_128_vehicles.csv"
print("Investigating data from file: ", file1)
unclassified_bins = create_bins(file1, 38, 80, 2)
print("bins: ", unclassified_bins)
var, index = otsu(unclassified_bins)
print("lowest mixed var: ", var)
print("bin ( %s ) index: %s" % (get_bin_speed(index, 38, 2), index))
print("bin[%s] = %s" % (index, unclassified_bins[index]))
print()

file2 = "Mystery_Data.csv"
print("Investigating data from file: ", file2)
mystery_bins = create_bins(file2 , 6, 40, 2)
print("bins: ", mystery_bins)
var, index = otsu(mystery_bins)
print("lowest mixed var: ", var)
print("bin ( %s ) index: %s" % (get_bin_speed(index, 6, 2), index))
print("bin[%s] = %s"%(index, mystery_bins[index]))


