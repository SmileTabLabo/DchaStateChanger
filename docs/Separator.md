## セパレーター機能について

一般的に､ ファイヤーウォールやウイルス対策ソフト側で設定されている場合があります｡  
ルーター側の設定を変えたり､ ウイルス対策ソフトの保護をオフにする必要が有る場合があります｡

### Windows Firewall の場合
※ Charles がデフォルトパスにインストールされている事を前提とします｡

｢**コマンド プロンプト**｣もしくは｢**Windows PowerShell**｣を管理者として実行します｡  
次に､ 以下のコマンドを実行します｡

```cmd
netsh advfirewall firewall add rule name="Charles Web Debugging Proxy" dir=in action=allow program="%PROGRAMFILES%\Charles\Charles.exe" enable=yes profile=public,private
```

この状態でもう一度 Charles での作業を行うと､ 正常に通ります｡  

これで無理だった場合は､ **Windows Firewall**以外の問題です｡  
各自で調べて下さい｡
