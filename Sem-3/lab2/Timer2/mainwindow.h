#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include "timer.h"
#include <QMainWindow>
#include <QToolBar>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QTimer>
#include <QTime>
#include <QLabel>
#include <QSplitter>
#include <QListWidget>
#include <QApplication>
#include <QFontDatabase>
#include <QList>
#include <QMessageBox>
#include <QTextEdit>
#include <QTimeEdit>
#include <QCheckBox>


class MainWindow : public QMainWindow
{
    Q_OBJECT
public:
    MainWindow();
    QTime temp;
private:

//Toolbar
    QToolBar *toolbar;
    void setToolBar();
    QLabel *timelbl;
//Main window widgets
    QLabel *doNotLbl;
    QCheckBox *doNotCheckBox;

    QLabel *mainTimerLbl;
    QListWidget *listW;
    QList<Timer> timers;

    bool stopped = false;
    bool doNotDisturb = false;

//Add window
    QWidget *addWindow;
    QLabel *addTimerLbl;
    QLabel *addDescLbl;
    QLabel *addCountLbl;
    QTimeEdit *addTimeEdit;
    QLineEdit *addTextEdit;
    QLineEdit *addCountTextEdit;
    void addTimer();
//Signal Window
   QWidget *signalWindow;
   QLabel *timeoutLbl;
   QTextEdit *timeoutDescLbl;
   QPushButton *timeoutOKBtn;
   void timeoutWindow();
//Edit Window
   QWidget *editWindow;
   QLabel *editTimeLbl;
   QLabel *editDescLbl;
   QTimeEdit *editTimeEdit;
   QLineEdit *editDescEdit;
   QPushButton *editTimerBtn;
   void toEditWindow();
//Info Window
//   QWidget *settingsWindow;
//   QPushButton *infoOKBtn;

private slots:
    void addTimerBtnClicked();
    void stopTimer();
    void editTimerBtnClicked();
    void deleteTimer();
    void deleteAllTimers();
    void doNotDisturbChanged(bool checked);
//    void settingsWindowSlot();
//    void infoOKBtnClicked();
private:
    void timerEvent(QTimerEvent *e);
    void timersSort();
    void updateTimersListW();

};
#endif // MAINWINDOW_H
