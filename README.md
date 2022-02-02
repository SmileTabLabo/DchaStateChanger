# Cpad\_dcha\_3\_changer

## 警告

このツールの使用によって起きた損害については保証しません。

ver 2.1にはバグがあります。使用できないためver 1.0を使用してください。
## 必要なもの

*   チャレンジパッドNEO(それ以前の機種はもっと楽に改造できます。自分で調べてください。)
*   Windows,Linux,Mac OS搭載のPC(FreeBSDではできるかわかりません。)
*   USB Type B Miniケーブル

## 準備

１，Charlesを以下のURLからインストールします。

[https://www.charlesproxy.com/download/](https://www.charlesproxy.com/download/)

無料版と有料版がありますが無料版で問題ありません。無料版は３０分で強制終了されるのでその都度再起動してください。設定は保存されるので手順を遡る必要はありません。

２，初期化済みチャレンジパッドNEOの電源を入れ、PCと同じwi-fiに接続します。

３，チャレンジパッドのipアドレスを調べます。設定アプリを開き、以下のように開きます。

設定→ネットワークとインターネット→Wi-Fi→Wi-Fi設定→詳細設定

そこにIPアドレスという項目があります。192.168.から始まる行をメモしておいてください。

例：192.168.11.30

## インストール

１，まずPCでCharlesを開きます。

![](https://user-images.githubusercontent.com/46545607/150069973-a1caa0d8-ec52-4cd9-8139-6860caa39fdb.png)

２，Proxy→Proxy settingsを開き、画像のように設定します。

![](https://user-images.githubusercontent.com/46545607/150070449-2f8413de-e142-429e-886a-f1d40fd584da.png)

![](https://user-images.githubusercontent.com/46545607/150070448-0f03137a-52b5-4639-b78b-d48ae29ed0b5.png)

設定が終わったらOKを押します。

３，Proxy→Access control settingsを開きます。

![](https://user-images.githubusercontent.com/46545607/150070951-0e2c40dc-e021-4ec9-8a11-9ff7a34e9c1c.png)

Addを押して先程調べたIPアドレスを入力します。ここでは192.168.11.30です。

入力後はOKを押します。

４，Proxy→SSL Proxying Settingsを開き、画像のように設定します。

![](https://user-images.githubusercontent.com/46545607/150072166-354e90b7-f560-4913-8af9-a7db4876e859.png)

設定後OKを押します。

５，Tools→Rewriteを開きます。

![](https://user-images.githubusercontent.com/46545607/150072490-991cc50a-9941-46b0-a795-0c2e254244e2.png)

６，Addを押してUntitled Setを追加後Untitled Setを選択します。

![](https://user-images.githubusercontent.com/46545607/150072831-16124fc9-641c-4610-9276-abaa24988852.png)

７，上の画像のような画面が出るので、上部のAddを押します。

![](https://user-images.githubusercontent.com/46545607/150072963-1fa59517-96c2-4eab-9ec3-3b216d343536.png)

上の画像のように設定します。設定値は次のとおりです。

Protocol: https

Host: townak.benesse.ne.jp

Port: 443

Path: rel/A/sp\_84/open/TouchSetupLogin.apk

Query: 空欄

設定後OKを押します。

８，６の画像下部のAddを押します。

![](https://user-images.githubusercontent.com/46545607/150074064-2ff7cf34-d2a6-4466-9ec8-ca3a68d2ef0a.png)

設定値は次のとおりです。

Type: URL

Match

 Value: [https://townak.benesse.ne.jp/rel/A/sp_84/open/TouchSetupLogin.apk](https://townak.benesse.ne.jp/rel/A/sp_84/open/TouchSetupLogin.apk)

Replace

 Value: [https://github.com/mouseos/Cpad_dcha_3_changer/releases/download/Ver1.0/dcha_state_3_changer_1.0.apk](https://github.com/mouseos/Cpad_dcha_3_changer/releases/download/Ver1.0/dcha_state_3_changer_1.0.apk)

Repace All選択

設定後OKを押します。

９，Help→SSL Proxying→Save Charles Root Certificateから証明書ファイルを保存します。保存場所はわかりやすい場所にしてください。

１０，保存した証明書の拡張子を.pemから.cerに変更します。Windowsで拡張子が表示されない人は「windows　拡張子　表示」と検索してください。

１１，Help→Local IP Addressesを開きPCのIPアドレスをメモします。192.168.で始まるものが必要です。

![](https://user-images.githubusercontent.com/46545607/150076625-62e2fc3f-8d75-4fc3-8f99-d543ef76d892.png)

この場合は192.168.11.9です。

１2，チャレンジパッドとPCをUSB接続します。チャレンジパッドの通知領域に「Androidシステム　この端末をUSBで充電中」と表示されているのでタップしてUSBの設定を開きます。「USBの使用」の項目で「ファイル転送」を選択してください。

１3．PCのファイルマネージャーにチャレンジパッドが表示されます。先程保存した証明書ファイルを「チャレンジパッド→内部共有ストレージ」にコピーしてください。

１4，チャレンジパッドの設定から次のように開きます。

設定→ネットワークとインターネット→Wi-Fi→（接続中のWi-FiのSSID名）→右上編集ボタン（ペンの形）→詳細設定

１５，プロキシを「なし」から手動に設定します。設定項目は以下のとおりです。

プロキシのホスト名:先程調べたPCのIPアドレス。

プロキシポート: 8888

プロキシをバイパス: 空欄

設定後は保存をタップします。

１６，Wi-Fi一覧画面に戻ってWi-Fi設定→詳細設定→証明書のインストールを開きます。

左側のメニューを開きTAB-A05-BDを開きます。（チャレンジパッドNEOの場合の表記）。

指紋アイコンの証明書ファイルがあるので選択します。

１７，「証明書の名前を指定する」といったダイアログが開くので名前を何でも良いのでつけてOKを押します。

１８，パスワード設定を要求されるので設定します。

１９，設定トップ画面に戻り、次のように開きます。

設定→セキュリティと現在地情報→画面ロック

パスワードが要求されるので入力後「なし」を選択します。

２０，ホーム画面に戻り、スタートボタンを押します。

![](https://user-images.githubusercontent.com/46545607/150078012-698eb423-1b02-4a76-8ad0-541a908d3862.jpg)

２１，画面に従って初期設定をします。

２３，アップデートの赤い進捗バーが１００％になると以下のような画面が出ます。dcha state を３にすることに成功しました。

![](https://user-images.githubusercontent.com/46545607/150080601-4ed2327d-710e-4d9f-b710-cf26e6081063.jpg)

※ユーザー情報入力画面やエラーが出た場合は設定にミスがあります。戻って確認してください。

２４，ホームから設定を開き、以下のように開発者向けオプションを開きます。

設定→システム→タブレット情報→ビルド番号連打

システムに戻って

開発者向けオプションを開く。

※パスワード要求された場合は失敗です。２０番に戻ってください。

２５，USBデバッグをオンにします。

２６，PCで以下のアプリのapkファイルをダウンロードします。

CPad Customize Tool

[https://github.com/Kobold831/CPadCustomizeTool/releases](https://github.com/Kobold831/CPadCustomizeTool/releases)

Nova Launcher

[https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher](https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher)

Aurora store for NEO

https://ctabwiki.nerrog.net/?neo-aurora

２７,ダウンロードしたapkのあるディレクトリで以下のコマンドを実行します。

adb install それぞれのapkのファイル名

adb shell dpm set-device-owner com.aurora.store/.data.receiver.AdministratorReceiver

※adbコマンドがない場合はインストールしてください。入れ方は各自調べてください。

28,チャレンジパッドのホームボタンを押してNova launcherを選択します。

２９，ランチャーからカスタマイズツールを開きます。

３０．システムUIを学習用に変更とナビゲーションバーの表示を維持とデバイス管理者を有効にするをオンにします。

３１，これで改造完了です。
