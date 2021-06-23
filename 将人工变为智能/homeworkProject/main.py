import pandas as pd
from pandas import DataFrame
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
import os
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
import boto3



if __name__ =='__main__':
    dataset_path = os.path.abspath("water_potability.csv")

    df = pd.read_csv(dataset_path)

    df = df.fillna(df.mean())

    target = df.get('Potability')

    train = df.drop(columns=['Potability'], axis=1)

    X_train, X_test, y_train, y_test = train_test_split(train, target, test_size=0.25, random_state=2021)

    rf = RandomForestRegressor()

    rf.fit(X_train, y_train)

    y_pred = rf.predict(X_test)

    threshold = 0.5

    for i in range(len(y_pred)):
        if y_pred[i] >= threshold:
            y_pred[i] = 1
        else:
            y_pred[i] = 0

    # 评估结果
    print("*以下是评估结果*")
    accuracy = accuracy_score(y_test, y_pred)
    print("accuracy: ", end='')
    print(accuracy)

    precision = precision_score(y_test, y_pred)
    print("precision: ", end='')
    print(precision)

    recall = recall_score(y_test, y_pred)
    print("recall: ", end='')
    print(recall)

    f1 = f1_score(y_test, y_pred)
    print("f1 score: ", end='')
    print(f1)

    X_test = X_test.reset_index(drop=True)
    y_predt = DataFrame(y_pred)
    y_predt.columns = ['predict']
    result = pd.concat([X_test, y_predt], axis=1)
    print(result)
    # 将结果写入.csv中
    result.to_csv('result_prediction.csv', index=0)
    print("写出.csv文件成功！")
    # 将结果保存在s3中
    print("正在上传到s3...")
    aws_bucket_name = 'zhangziyi'
    s3 = boto3.client('s3',
                      aws_access_key_id='3DADC8A072AED72C12D3',
                      aws_secret_access_key='Wzk3NUUwRTU3RUQzNTFGRTBDNDY3RDI2RjdEOUY0',
                      endpoint_url='http://scut.depts.bingosoft.net:29997')
    s3.upload_file('result_prediction.csv', 'zhangziyi', 'result_prediction.csv')
    print("上传到s3成功！")






