import math
import csv
import matplotlib.pyplot as plt
__author__ = 'Tyler'


def misclassification_rate(thresholds,length):
    """
    Finds the index for the speed.
    :param thresholds: bins for the speeds. 
    :param length: number of entries in the bin.
    :return:
            best_missclassification_rate - best miss classification rate in the thresholds given.
            best_threshold - best threshold with the lowest miss classification rate
    """
    best_threshold = 0
    best_misclassification_rate = float("inf")
    miscalc_list = []
    best_false_alarms = float("inf")
    true_pos_list = []
    false_pos_list = []
    best_true_positive = 0
    best_true_negative = 0
    best_misses = 0
    #loop through each index in the thresholds.
    for threshold in range(len(thresholds)):
        num_false_alarms = 0
        num_misses = 0
        num_true_negatives = 0
        num_true_positives = 0
        #loops through all of the thresholds to calculate the miss classification rate for a given threshold index
        for test_threshold in range(len(thresholds)):
            #loop through each entry in the bin to get the intention of recklesness driver
            for i in range(len(thresholds[test_threshold])):
                if test_threshold <= threshold:
                    if thresholds[test_threshold][i][1] == 1:
                        num_misses += 1
                    else:
                        num_true_negatives += 1
                else:
                    if thresholds[test_threshold][i][1] == 1:
                        num_true_positives += 1
                    else:
                        num_false_alarms += 1
        # calculate the miss calculation rate and put into the list.
        miscalculation_rate = (num_misses + num_false_alarms) / length
        # lists used for graphing
        miscalc_list.append(miscalculation_rate)
        false_pos_list.append(num_false_alarms/(num_false_alarms+num_true_negatives))
        true_pos_list.append(num_true_positives/(num_true_positives + num_misses))

        # set the best miscalculation rate variables
        if miscalculation_rate <= best_misclassification_rate and num_false_alarms < best_false_alarms:
            best_misclassification_rate = miscalculation_rate
            best_threshold = threshold
            best_false_alarms = num_false_alarms
            best_misses = num_misses
            best_true_negative = num_true_negatives
            best_true_positive = num_true_positives

    # graph the ROC curve
    plt.plot(false_pos_list,true_pos_list)
    plt.plot(false_pos_list[best_threshold], true_pos_list[best_threshold],'o')  # add a circle to the lowest threshold
    plt.ylabel("Correct Hit Rate")
    plt.xlabel("False Alarm Rate")
    plt.title("ROC Curve by Threshold")
    plt.draw()

    # create a new figure to graph on and plot the miscalculation rate
    plt.figure()
    plt.plot(miscalc_list)
    plt.xlabel("Bin")
    plt.plot(best_threshold, miscalc_list[best_threshold], 'o')
    plt.ylabel("Miscalculation Rate")
    plt.title("Misclassification Rate by Threshold")
    plt.show()

    print("Best miss rate: ",best_misses)
    print("Best true positive rate: ",best_true_positive)
    print("Best true negative rate: ",best_true_negative)
    print("Best false alarm rate: ",best_false_alarms)
    print("False Pos Rate: ",false_pos_list[best_threshold])
    print("True Pos Rate: ",true_pos_list[best_threshold])

    return best_misclassification_rate, best_threshold



def get_bin_index(index_speed, index_min_speed, index_bin_size):
    """
    Finds the index for the speed.
    :param index_speed: speed to index
    :param index_min_speed: min speed for the bin
    :param index_bin_size: size of the bin
    :return: index of the bin
    """
    return int(math.floor((index_speed-index_min_speed)/index_bin_size))


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
    print("max bin: ", max_bin)
    bins = [[] for i in range(max_bin)]
    csv_list_speed = []
    csv_list_reckless = []
    count = 0
    # split the data into bins of size 2 in a range from 38-80
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        for line in reader:
            print(line[0])
            csv_list_speed.append(float(line[0]))
            csv_list_reckless.append(int(line[1]))
            speed = float(line[0])
            is_speeding = int(line[1])
            # get the bin for the speed
            index = get_bin_index(speed, min_speed, bin_size)
            if index < 0 or index > max_bin:
                print("index out of range, skipping value: ",speed)
                continue
            else:
                assert index < max_bin
                bins[index].append((speed,is_speeding))
                count += 1

    return bins,csv_list_speed,csv_list_reckless,count

file1= "CLASSIFIED_TRAINING_SET_FOR_RECKLESS_DRIVERS.csv"
print("Investigating data from file: ", file1)
reckless_drivers_bins,reckless_drivers_speed,reckless_drivers_isreckless, length = create_bins(file1, 40, 72, .5);

# check if we added all of the speeds
assert length == len(reckless_drivers_speed) and length == len(reckless_drivers_isreckless)
print("bins: ", reckless_drivers_bins);
print("")
print("speed min: ", min(reckless_drivers_speed))
print("speed max: ", max(reckless_drivers_speed))
miscalc_rate, threshold = misclassification_rate(reckless_drivers_bins, length)
print("")
print("Best miscalculation rate: ",miscalc_rate)
print("Best threshold: ",threshold)
print("bin : ", reckless_drivers_bins[threshold])





