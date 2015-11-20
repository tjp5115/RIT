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
            Root-> (2.8, 0)
            Root->Left-> (5.8, 1)           <  root
            Root->Right-> (8.2, 0)          >  root
            Root->Right->Left-> (7.8, 0)    <  root->right #dud already < root->right(8.2)
            Root->Right->Right (5.8, 1)     >  root->right
            Root->Left->Left (2.8, 0)       <  root->left  #dud already < root(2.8)
            Root->Left->Right (2.4, 0)      >  root->left  #dud already < root(2.8)
    """
    threshold_a,index_a = 2.8, 0 # root node
    threshold_b,index_b = 5.8, 1 # < root   root->left
    threshold_c,index_c = 8.2, 0 # > root   root->right
    threshold_e,index_e = 5.8, 1 # > root->right    root->right->right
    values = []
    for line in data:

        if line[index_a] < threshold_a:  #root
           #root->left
            if line[index_b] < threshold_b:
               values.append(0)
            else:
                values.append(1)
        else: #root
            #root->right
            if line[index_c] < threshold_c:
                values.append(1)
            else:
                #root->right->right
                if line[index_e] < threshold_e:
                    values.append(1)
                else:
                    values.append(0)

    return values;

# lists to perform calcs on

# test data
data_test = scan_csv("HWBNS_02_Training__v027_bonus.csv")
# training data to determine how accurate the decision tree was for the training set.
data_train = scan_csv("HWBNS_02_Training__v027_bonus.csv")

# array for the results
data_test_values = classifier(data_test)
data_train_values = classifier(data_train)

# calculated the accuracy of the training data
accuracy = 0
for i in range(len(data_train)):
    if data_train[i][-1] == data_train_values[i]:
        accuracy += 1

print("Training data accuracy: ",accuracy/len(data_train))
data_train = scan_csv("HWBNS_02_Testing__Data__v027_bonus.csv")
data_train_values = classifier(data_train)
# output the test data to a file.
f = open("HW_BNS_02_Paulsen_Tyler_MyClassifications.txt.",'w')
for e in data_train_values:
    f.write(str(e)+"\n")


