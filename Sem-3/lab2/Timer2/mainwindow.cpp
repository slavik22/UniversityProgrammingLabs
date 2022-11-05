#include "mainwindow.h"
#include <QDebug>
#include <QScrollBar>
#include <QPushButton>
#include <QLineEdit>
#include <QtMultimedia/QMediaPlayer>
MainWindow::MainWindow()
{
    doNotLbl = new QLabel("Do not disturbe mode");
    doNotCheckBox = new QCheckBox();
    doNotCheckBox->setChecked(false);
    doNotDisturb = false;

    temp.setHMS(0,0,0);
    setToolBar();

    QLabel *tmr = new QLabel("Nearest Timer",this);
    tmr->setGeometry(50,85,100,20);
    mainTimerLbl = new QLabel(this);
    mainTimerLbl->setText("00:00:00");
    mainTimerLbl->setGeometry(40,100,130,40);

    QFont font = mainTimerLbl->font();
    font.setPixelSize(32);
    mainTimerLbl->setFont(font);

    QLabel *tmrslbl = new QLabel("Timers",this);
    tmrslbl->setGeometry(300,60,50,20);

    QScrollBar *sb = new QScrollBar(Qt::Vertical);

    listW = new QListWidget(this);
    listW->setVerticalScrollBar(sb);
    listW->setGeometry(300,80,200,200);
}

void MainWindow::timerEvent(QTimerEvent *e)
{
    Q_UNUSED(e);

    timelbl->setText(QTime::currentTime().toString());

    if(stopped) return;

    for(int i = 0; i < timers.size(); i++){
            timers[i].setTime(timers[i].getTime().addMSecs(-1000));
            listW->item(i)->setText(timers[i].getTime().toString() + " | " + timers[i].getDesc());
        }

     for(int i = 0; i < timers.size(); i++){
            if(timers[i].getTime() == temp){
                timeoutWindow();
                timers.removeAt(i);
                delete listW->takeItem(i);
             }
        }

     if(timers.empty())
         mainTimerLbl->setText("00:00:00");
     else
         mainTimerLbl->setText(timers[0].getTime().toString());

}

void MainWindow::setToolBar()
{
    toolbar = addToolBar("Main Toolbar");

    toolbar->addSeparator();
    QAction *add = toolbar->addAction("New Timer");
    QAction *stop = toolbar->addAction("Stop timers");
    QAction *edit = toolbar->addAction("Edit Timer");
    QAction *dlete = toolbar->addAction("Delete Timer");
    QAction *dleteAll = toolbar->addAction("Delete All Timers");
    QAction *info = toolbar->addAction("Timer's Info");
    toolbar->setMovable(false);
    toolbar->addSeparator();

    QLabel *lbl = new QLabel("Current time: ");

    toolbar->addWidget(lbl);

    timelbl = new QLabel(QTime::currentTime().toString());
    startTimer(1000);
    toolbar->addWidget(timelbl);

    connect(stop, &QAction::triggered, this, &MainWindow::stopTimer);
    connect(add, &QAction::triggered, this, &MainWindow::addTimer);
    connect(edit, &QAction::triggered, this, &MainWindow::toEditWindow);
    connect(dlete, &QAction::triggered, this, &MainWindow::deleteTimer);
    connect(dleteAll, &QAction::triggered, this, &MainWindow::deleteAllTimers);
    connect(info, &QAction::triggered, this, &MainWindow::settingsWindowSlot);
}

void MainWindow::addTimer()
{
    addWindow = new QWidget();
    addWindow->resize(250,200);
    addWindow->setWindowTitle("Add Timer");

    addTimerLbl = new QLabel("Time");
    addTimerLbl->setGeometry(20,60,50,20);

    addTimeEdit = new QTimeEdit();
    addTimeEdit->setDisplayFormat("hh:mm:ss");
    addTimeEdit->setGeometry(90,65,100,25);

    addDescLbl = new QLabel("Description");
    addDescLbl->setGeometry(20, 100, 50, 20);

    addTextEdit = new QTextEdit();
    addTextEdit->setGeometry(90,130,100,150);

    addCountLbl = new QLabel("Count of timers");
    addCountTextEdit = new QTextEdit();

    QPushButton *addBtn = new QPushButton("Set Timer",addWindow);

    addWindow->setLayout(new QVBoxLayout());
    addWindow->layout()->addWidget(addTimerLbl);
    addWindow->layout()->addWidget(addTimeEdit);
    addWindow->layout()->addWidget(addDescLbl);
    addWindow->layout()->addWidget(addTextEdit);
    addWindow->layout()->addWidget(addCountLbl);
    addWindow->layout()->addWidget(addCountTextEdit);
    addWindow->layout()->addWidget(addBtn);

    connect(addBtn, &QPushButton::clicked, this, &MainWindow::addTimerBtnClicked);
    addWindow->show();
}

void MainWindow::timeoutWindow()
{
    QVBoxLayout *vbox = new QVBoxLayout();

    QMediaPlayer* player = new QMediaPlayer();
    player->setSource(QUrl::fromLocalFile(":/resources/resources/AlarmClock.wav"));
    player->play();

    signalWindow = new QWidget();
    signalWindow->resize(250,200);
    signalWindow->setObjectName("TIMEOUT!");
    timeoutLbl = new QLabel("Timeout! There is description:");
    timeoutDescLbl = new QTextEdit(this);
    timeoutDescLbl->setText(timers[0].getDesc());
    timeoutDescLbl->setReadOnly(true);
    timeoutOKBtn = new QPushButton("OK", this);

    vbox->addWidget(timeoutLbl);
    vbox->addWidget(timeoutDescLbl);
    vbox->addWidget(timeoutOKBtn);

    connect(timeoutOKBtn, &QPushButton::clicked, signalWindow, &QPushButton::close);

    signalWindow->show();
    signalWindow->setLayout(vbox);
}

void MainWindow::toEditWindow()
{

    if(timers.empty()){
        QMessageBox::warning(this,tr("Timers is empty"),tr("Timers is empty"));
        return;
        }

    editWindow = new QWidget();
    editWindow->resize(200,250);
    editWindow->setObjectName("Edit Timer");

    QVBoxLayout *vbox = new QVBoxLayout();

    editTimeLbl = new QLabel("Edit time:");
    editDescLbl = new QLabel("Edit description:");
    editTimeEdit = new QTimeEdit();
    editTimeEdit->setDisplayFormat("hh:mm:ss");
    editDescEdit = new QTextEdit();
    editTimerBtn = new QPushButton("OK");

    vbox->addWidget(editTimeLbl);
    vbox->addWidget(editTimeLbl);
    vbox->addWidget(editTimeEdit);
    vbox->addWidget(editDescLbl);
    vbox->addWidget(editDescEdit);
    vbox->addWidget(editTimerBtn);

    connect(editTimerBtn, &QPushButton::clicked, this, &MainWindow::editTimerBtnClicked);
    editWindow->setLayout(vbox);
    editWindow->show();
}

void MainWindow::addTimerBtnClicked()
{
    QTime time(addTimeEdit->time().hour(),addTimeEdit->time().minute(),addTimeEdit->time().second());
    Timer timer(time,addTextEdit->toPlainText());
    for(int i = 0; i < addCountTextEdit->toPlainText().toInt(); i++){
        timers.append(timer);
    }
    addWindow->close();
    timersSort();

    if(timers.empty()){
        mainTimerLbl->setText("00:00:00");
    } else {
        mainTimerLbl->setText(timers[0].getTime().toString());
    }
    updateTimersListW();
}

void MainWindow::stopTimer()
{
   stopped = !stopped;
}

void MainWindow::editTimerBtnClicked()
{
    QTime time(editTimeEdit->time().hour(),editTimeEdit->time().minute(),editTimeEdit->time().second());
    int positionToEditt = listW->currentRow();

    Timer *t = &timers[positionToEditt];

    t->setTime(time);
    t->setDesc(editDescEdit->toPlainText());

    listW->item(positionToEditt)->setText(t->getTime().toString() + " | " + t->getDesc());

    editWindow->close();
    timersSort();
}

void MainWindow::deleteTimer()
{
    if(timers.empty()){
        QMessageBox::warning(this,tr("Timers is empty"),tr("Timers is empty"));
        return;
        }


    int positionToRemove = listW->currentRow();
    timers.removeAt(positionToRemove);
    delete listW->takeItem(positionToRemove);
    if(timers.empty())
        mainTimerLbl->setText("00:00:00");
}

void MainWindow::deleteAllTimers()
{
    timers.clear();
    listW->clear();
    mainTimerLbl->setText("00:00:00");
}

void MainWindow::settingsWindowSlot()
{
    settingsWindow = new QWidget;
    QVBoxLayout *vbox = new QVBoxLayout();
    QHBoxLayout *hbox = new QHBoxLayout();

    infoOKBtn = new QPushButton("OK");

    hbox->addWidget(doNotLbl);
    hbox->addWidget(doNotCheckBox);
    vbox->addLayout(hbox);
    vbox->addWidget(infoOKBtn);

    settingsWindow->setLayout(vbox);
    settingsWindow->setObjectName("Settings");
    settingsWindow->setFixedSize(250,150);
    settingsWindow->show();

    connect(infoOKBtn, &QPushButton::clicked, this, &MainWindow::infoOKBtnClicked);
}

void MainWindow::infoOKBtnClicked()
{
    doNotDisturb = doNotCheckBox->isChecked();
    settingsWindow->close();
}

void MainWindow::timersSort() {
    if(timers.size() > 1) {
        int i, j;
        for (i = 0; i < timers.size()-1; i++)
        for (j = 0; j < timers.size()-i-1; j++){
            if (timers[j].getTime() > timers[j+1].getTime()){
                Timer tmp = timers[j];
                timers[j] = timers[j+1];
                timers[j+1] = tmp;
            }
        }
    } else return;
}

void MainWindow::updateTimersListW()
{
    listW->clear();
    for(int i = 0; i < timers.size(); i++){
        listW->addItem(timers[i].getTime().toString() + " | " + timers[i].getDesc());
    }
}
