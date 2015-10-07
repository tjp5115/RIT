import csv

__author__ = 'Tyler Paulsen'

def scan_csv(file):
    """
    scan a csv file and return the results as a multidimensional array of floating point numbers
    :param file: csv file
    :return: multidimentional array of floating point numbers
    """
    dec_list = []
    with open(file) as csv_file:
        reader = csv.reader(csv_file)
        # Skip the first line
        next(reader, None)
        for line in reader:
            dec_list.append([float(i) for i in line])
    return dec_list

def classifier(data):
    """
    classifies the data list based on the decision tree.
    :param data: list to classify
    :return: list of values - 1 to 1 map to the list
    """
    threshold_a,index_a = 4.2,0
    threshold_b,index_b = 3.3, 1
    # threshold c is so high, that it will almost always be a 0
    threshold_c,index_c = 9.5, 1
    values = []
    for line in data:
        if line[index_a] < threshold_a:
            if line[index_b] < threshold_b:
                values.append(0)
            else:
                values.append(1)
        else:
            if line[index_c] < threshold_c:
                values.append(0)
            else:
                values.append(1)
    return values;

# lists to perform calcs on

# test data
data_test = scan_csv("HW04_Testing__Data__FOR_RELEASE__v010.csv")
# training data to determine how accurate the decision tree was for the training set.
data_train = scan_csv("HW04_Training_Data__v010.csv")

# array for the results
data_test_values = classifier(data_test)
data_train_values = classifier(data_train)

# calculated the accuracy of the training data
accuracy = 0
for i in range(len(data_train)):
    if data_train[i][-1] == data_train_values[i]:
        accuracy += 1

print("Training data accuracy: ",accuracy/len(data_train))

# output the test data to a file.
f = open("HW04_Paulsen_Tyler__MyClassifications.txt.",'w')
for e in data_train_values:
    f.write(str(e)+"\n")


