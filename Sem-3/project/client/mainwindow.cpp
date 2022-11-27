#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QInputDialog>
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    socket = new QTcpSocket(this);
    connect(socket, &QTcpSocket::readyRead, this, &MainWindow::slotReadyRead);
    connect(socket, &QTcpSocket::disconnected, this, &MainWindow::deleteLater);
    connect(socket, SIGNAL(stateChanged(AbstractSocket::SocketState)),
                this, SLOT(HandleStateChange(AbstractSocket::SocketState)));

    nextBlockSize = 0;
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    socket->connectToHost("127.0.0.1",2323);
    isConnected = true;
    ui->pushButton->setEnabled(false);
}

void MainWindow::SendToServer(QString str)
{
    if(!isConnected){
        QMessageBox::warning(this, "Warning","Client is not connected to server");
        return;
    }

    if(ui->lineEdit->text().isEmpty()){
        QMessageBox::warning(this, "Warning","Message is empty");
        return;
    }

    if(UserName.isNull()){
        bool ok;

        QString text = QInputDialog::getText(0, "User name",
                                             "Your name:", QLineEdit::Normal,
                                             "", &ok);

        if(ok && !text.isEmpty()){
            this->UserName = text;
        }
        else{
            QMessageBox::warning(this, "Warning","Client name is empty");
            return;
        }
    }

    Data.clear();
    QDataStream out(&Data, QIODevice::WriteOnly);
    out.setVersion(QDataStream::Qt_6_3);
    out << quint16(0) << UserName << QTime::currentTime() << str;
    out.device()->seek(0);
    out << qint16(Data.size() - sizeof(quint16));
    socket->write(Data);
    ui->lineEdit->clear();
}

void MainWindow::slotReadyRead()
{
    QDataStream in(socket);
    in.setVersion(QDataStream::Qt_6_3);
    if(in.status() == QDataStream::Ok){
        for(;;){
            if(nextBlockSize == 0){
                if(socket->bytesAvailable() < 2){
                    break;
                }
                in >> nextBlockSize;
            }
            if(socket->bytesAvailable() < nextBlockSize){
                break;
            }

            QString str;
            QString userName;
            QTime time;
            in >> userName >> time >> str;
            nextBlockSize = 0;
            ui->textBrowser->append(userName + ":");
            ui->textBrowser->append(time.toString() + " " + str);
        }

    }
    else{
       ui->textBrowser->append("read error");
    }
}


void MainWindow::on_pushButton_2_clicked()
{
    SendToServer(ui->lineEdit->text());
}


void MainWindow::on_lineEdit_returnPressed()
{
    SendToServer(ui->lineEdit->text());
}

