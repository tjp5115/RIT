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

    """
        values are an interpretation from the tree:
        Gini:
            Root-> (5.8, 1)
            Root->Left-> (2.8, 0)
            Root->Right-> (9.8, 0)
            Root->Right->Left-> (9.7, 0)
            Root->Right->Right None
            Root->Left->Left (2.8, 0)
            Root->Left->Right (7.8, 0)
    """
    threshold_a,index_a = 5.8, 1 # root node
    threshold_b,index_b = 2.8, 0 # left left split
    threshold_c,index_c = 7.8, 0 # left right split
    values = []
    for line in data:

        if line[index_a] > threshold_a: #if >, take the right path of the tree
            #since the right side of the tree is all off the chart (9.7 and above)
            # everything is of class 1
            values.append(1)
        else: #left side of the tree
            if line[index_b] > threshold_b: #root->left move
                if line[index_c] > threshold_c: #root->left->right
                    values.append(0)
                else:
                    values.append(1)
            else: # root->left->left is same as root->left, so we can assume it is all the same classification
                values.append(0)
    return values;

# lists to perform calcs on

# test data
data_test = scan_csv("HWBNS_01_Testing__Data__v026_bonus.csv")
# training data to determine how accurate the decision tree was for the training set.
data_train = scan_csv("HWBNS_01_Training__v026_bonus.csv")

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
data_train = scan_csv("HWBNS_01_Testing__Data__v026_bonus.csv")
data_train_values = classifier(data_train)
f = open("HW_BNS_01_Paulsen_Tyler_MyClassifications.txt.",'w')
for e in data_train_values:
    f.write(str(e)+"\n")


