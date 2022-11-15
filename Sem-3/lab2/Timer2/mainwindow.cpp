#include "mainwindow.h"
#include <QDebug>
#include <QScrollBar>
#include <QPushButton>
#include <QLineEdit>
#include <QSoundEffect>

MainWindow::MainWindow()
{
    this->setWindowIcon(QIcon(":/resources/icon2.png"));
    this->setFixedSize(QSize(550, 300));

    temp.setHMS(0,0,0);
    setToolBar();

    mainTimerLbl = new QLabel(this);
    mainTimerLbl->setText("00:00:00");
    mainTimerLbl->setGeometry(40,100,130,40);


    QLabel *doNotLb = new QLabel("Do not disturb:",this);
    doNotLb->setGeometry(40,160,120,20);

    doNotCheckBox = new QCheckBox(this);
    doNotCheckBox->setChecked(false);
    doNotCheckBox->setGeometry(140,160,20,20);

    connect(doNotCheckBox, SIGNAL(clicked(bool)), this, SLOT(doNotDisturbChanged(bool)));
    doNotDisturb = false;


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

void MainWindow::doNotDisturbChanged(bool checked){
    doNotDisturb = checked;
}

void MainWindow::timerEvent(QTimerEvent *e)
{
    Q_UNUSED(e);

    timelbl->setText(QTime::currentTime().toString());

    if(stopped) return;

    for(int i = 0; i < timers.size(); i++){
            timers[i].setTime(timers[i].getTime().addMSecs(-1000));
            listW->item(i)->setText(timers[i].getTime().toString() + " | " + timers[i].getDesc());

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

    QAction *add = toolbar->addAction("Add Timer");
    QAction *stop = toolbar->addAction("Stop timers");
    QAction *edit = toolbar->addAction("Edit Timer");
    QAction *dlete = toolbar->addAction("Remove Timer");
    QAction *dleteAll = toolbar->addAction("Remove All Timers");
    toolbar->setMovable(false);

    timelbl = new QLabel(QTime::currentTime().toString());
    startTimer(1000);
    toolbar->addWidget(timelbl);

    connect(stop, &QAction::triggered, this, &MainWindow::stopTimer);
    connect(add, &QAction::triggered, this, &MainWindow::addTimer);
    connect(edit, &QAction::triggered, this, &MainWindow::toEditWindow);
    connect(dlete, &QAction::triggered, this, &MainWindow::deleteTimer);
    connect(dleteAll, &QAction::triggered, this, &MainWindow::deleteAllTimers);
}

void MainWindow::addTimer()
{
    addWindow = new QWidget();
    addWindow->setWindowIcon(QIcon(":/resources/icon2.png"));
    addWindow->resize(250,200);
    addWindow->setWindowTitle("Add Timer");

    addTimerLbl = new QLabel("Time");
    addTimerLbl->setGeometry(20,60,50,20);

    addTimeEdit = new QTimeEdit();
    addTimeEdit->setDisplayFormat("hh:mm:ss");
    addTimeEdit->setMinimumTime(QTime(0,0,1));
    addTimeEdit->setGeometry(90,65,100,25);

    addDescLbl = new QLabel("Description");
    addDescLbl->setGeometry(20, 100, 50, 20);

    addTextEdit = new QLineEdit();
    addTextEdit->setGeometry(90,130,100,150);

    addCountLbl = new QLabel("Count of timers");
    addCountTextEdit = new QLineEdit();

    addCountTextEdit->setValidator(new QIntValidator(1,1,this));

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

    signalWindow = new QWidget();
    signalWindow->setWindowIcon(QIcon(":/resources/icon2.png"));

    signalWindow->resize(250,200);
    signalWindow->setObjectName("TIMEOUT!");
    timeoutLbl = new QLabel("Timeout!");

    QString description = timers[0].getDesc();
    description = description.isEmpty() ? "No description..." : description;
    QLabel *timeoutDescprPLbl = new QLabel("Description: " +  description);

    QFont font = timeoutDescprPLbl->font();
    font.setPixelSize(20);
    timeoutLbl->setFont(font);

    timeoutOKBtn = new QPushButton("OK", this);

    vbox->addWidget(timeoutLbl);
    vbox->addWidget(timeoutDescprPLbl);
    vbox->addWidget(timeoutOKBtn);

    connect(timeoutOKBtn, &QPushButton::clicked, signalWindow, &QPushButton::close);

    signalWindow->show();
    signalWindow->setLayout(vbox);

    if(!doNotDisturb){
        QSoundEffect* player = new QSoundEffect();
        player->setSource(QUrl::fromLocalFile(":/resources/AlarmClock.wav"));
        player->play();
    }
}

void MainWindow::toEditWindow()
{

    if(timers.empty()){
        QMessageBox::warning(this,tr("Timers are empty"),tr("Timers are empty"));
        return;
    }

    int positionToRemove = listW->currentRow();

    if(positionToRemove < 0){
        QMessageBox::warning(this, tr("ERROR"), tr("Timer is not selected"));
        return;
    }

    editWindow = new QWidget();
    editWindow->setWindowIcon(QIcon(":/resources/icon2.png"));
    editWindow->resize(250,150);
    editWindow->setObjectName("Edit Timer");

    QVBoxLayout *vbox = new QVBoxLayout();

    editTimeLbl = new QLabel("Edit time:");
    editDescLbl = new QLabel("Edit description:");
    editTimeEdit = new QTimeEdit();
    editTimeEdit->setMinimumTime(QTime(0,0,1));
    editTimeEdit->setDisplayFormat("hh:mm:ss");
    editDescEdit = new QLineEdit();
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
    Timer timer(time,addTextEdit->text());
    for(int i = 0; i < addCountTextEdit->text().toInt(); i++){
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
   if(timers.empty()){
        QMessageBox::warning(this,tr("Timers are empty"),tr("Timers are empty"));
        return;
    }
   stopped = !stopped;

}

void MainWindow::editTimerBtnClicked()
{
    QTime time(editTimeEdit->time().hour(),editTimeEdit->time().minute(),editTimeEdit->time().second());
    int positionToEditt = listW->currentRow();

    Timer *t = &timers[positionToEditt];

    t->setTime(time);
    t->setDesc(editDescEdit->text());

    listW->item(positionToEditt)->setText(t->getTime().toString() + " | " + t->getDesc());

    editWindow->close();
    timersSort();
}

void MainWindow::deleteTimer()
{
    if(timers.empty()){
        QMessageBox::warning(this,tr("Timers are empty"),tr("Timers are empty"));
        return;
     }

    int positionToRemove = listW->currentRow();

    if(positionToRemove < 0){
        QMessageBox::warning(this, tr("ERROR"), tr("Timer is not selected"));
        return;
    }

    timers.removeAt(positionToRemove);
    delete listW->takeItem(positionToRemove);
    if(timers.empty())
        mainTimerLbl->setText("00:00:00");
}

void MainWindow::deleteAllTimers()
{
    if(timers.empty()){
        QMessageBox::warning(this,tr("Timers are empty"),tr("Timers are empty"));
        return;
     }


    timers.clear();
    listW->clear();
    mainTimerLbl->setText("00:00:00");
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
