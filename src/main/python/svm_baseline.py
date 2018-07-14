import numpy as np
from sklearn import metrics
from sklearn.datasets import load_svmlight_file
from sklearn.linear_model import LogisticRegression
from sklearn.multiclass import OneVsRestClassifier
from sklearn.preprocessing import MultiLabelBinarizer

if __name__ == '__main__':
    # read files
    X_train, y_train = load_svmlight_file("data/train.libsvm", dtype=np.float64, multilabel=True)
    X_test, y_test = load_svmlight_file("data/CalcSummarySim.libsvm", dtype=np.float64, multilabel=True)

    # transform y into a matrix
    mb = MultiLabelBinarizer()
    y_train = mb.fit_transform(y_train)

    # fit the model and predict

    clf = OneVsRestClassifier(LogisticRegression(), n_jobs=-1)
    clf.fit(X_train, y_train)
    pred_y = clf.predict(X_test)

    # training set result
    y_predicted = clf.predict(X_train)

    # report
    print(metrics.classification_report(y_train, y_predicted))

    import numpy as np

    print(np.mean(y_predicted == y_train))
