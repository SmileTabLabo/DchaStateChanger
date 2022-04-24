# Cpad\_dcha\_3\_changer

## 警告

このツールの使用によって起きた損害については保証しません。

ver 2.1は初期設定からは使用できないためver 1.0を使用してください。
## 必要なもの

*   チャレンジパッドNEO/NEXT(それ以前の機種はもっと楽に改造できます。自分で調べてください。)
*   Windows,Linux,Mac OS搭載のPC(FreeBSDではできるかわかりません。)
*   USB Type B Miniケーブル

## 準備

Charlesを以下のURLからインストールします。

[https://www.charlesproxy.com/download/](https://www.charlesproxy.com/download/)

無料版と有料版がありますが無料版で問題ありません。無料版は３０分で強制終了されるのでその都度再起動してください。設定は保存されるので手順を遡る必要はありません。

## インストール

１，まずPCでCharlesを開きます。

![](https://user-images.githubusercontent.com/46545607/150069973-a1caa0d8-ec52-4cd9-8139-6860caa39fdb.png)

２，Proxy→Proxy settingsを開き、画像のように設定します。

![](https://user-images.githubusercontent.com/46545607/150070449-2f8413de-e142-429e-886a-f1d40fd584da.png)

![](https://user-images.githubusercontent.com/46545607/150070448-0f03137a-52b5-4639-b78b-d48ae29ed0b5.png)

設定が終わったらOKを押します。

３，Proxy→SSL Proxying Settingsを開き、画像のように設定します。

![](https://user-images.githubusercontent.com/52069677/164899196-0ddab9ba-6dae-489a-ba90-b8b108ad7a29.png)

![](https://user-images.githubusercontent.com/46545607/150072166-354e90b7-f560-4913-8af9-a7db4876e859.png)

設定後OKを押します。

４，Tools→Rewriteを開きます。

![](https://user-images.githubusercontent.com/46545607/150072490-991cc50a-9941-46b0-a795-0c2e254244e2.png)

Addを押してUntitled Setを追加後Untitled Setを選択します。

５，下の画像のような画面が出るので、上部のAddを押します。
![](https://user-images.githubusercontent.com/46545607/150072831-16124fc9-641c-4610-9276-abaa24988852.png)

下の画像のように設定します。<br>
![](https://user-images.githubusercontent.com/46545607/150072963-1fa59517-96c2-4eab-9ec3-3b216d343536.png)

設定値は次のとおりです。
```
Protocol: https

Host: townak.benesse.ne.jp

Port: 443

Path: rel/A/sp_84/open/TouchSetupLogin.apk

Query: (空欄)
```
設定後OKを押します。

６，５の画像下部のAddを押します。

![](https://user-images.githubusercontent.com/46545607/150074064-2ff7cf34-d2a6-4466-9ec8-ca3a68d2ef0a.png)

設定値は次のとおりです。
```
Type: URL

Match

 Value: https://townak.benesse.ne.jp/rel/A/sp_84/open/TouchSetupLogin.apk

Replace

 Value: https://github.com/mouseos/Cpad_dcha_3_changer/releases/download/Ver1.0/dcha_state_3_changer_1.0.apk
```
Repace Allを選択

設定後OKを押します。

７，Help→SSL Proxying→Save Charles Root Certificateからファイルのタイプを｢Binary certificate (.cer)｣にしてから証明書ファイルを保存します。<br>
![](https://user-images.githubusercontent.com/52069677/164907250-9a3b0ebc-d761-412d-b573-50b41dc855b8.png)<br>
保存場所はわかりやすい場所にしてください。

８，Help→Local IP Addressesを開きPCのIPアドレスをメモします。192.168.で始まるものが必要です。

![](https://user-images.githubusercontent.com/46545607/150076625-62e2fc3f-8d75-4fc3-8f99-d543ef76d892.png)

この場合は192.168.11.9です。

９，チャレンジパッドとPCをUSB接続します。チャレンジパッドの通知領域に「Androidシステム・この端末を USB で充電中」と表示されているのでタップしてUSBの設定を開きます。
![](https://user-images.githubusercontent.com/52069677/164910761-156ca2b0-11e3-4f51-9832-1ea2128c834b.png)<br>

「USB の使用」の項目で「ファイル転送」を選択してください。<br>
![](https://user-images.githubusercontent.com/52069677/164909609-7326b7d8-7b8d-4a6b-8ec8-0fa649316f3e.png)

１０．PCのエクスプローラーにチャレンジパッドが表示されます。先程保存した証明書ファイルを「チャレンジパッド→内部共有ストレージ」にコピーしてください。

１１，チャレンジパッドの設定から次のように開きます。

設定→ネットワークとインターネット→Wi-Fi→Wi-Fi 設定→詳細設定→証明書のインストール を開きます。

左側のメニューを開きTAB-A05-BDを開きます。（チャレンジパッドNEOの場合の表記）。

先程コピーした証明書ファイルがあるので選択します。

「証明書の名前を指定する」といったダイアログが開くので名前を何でも良いのでつけてOKを押します。
｢VPNとアプリ｣のまま変更しないでください。

パスワード設定を要求されるので設定します。

１２，証明書がインストール出来たら１つ前の画面に戻り、Wi-Fi の使用 を有効にします。
次に、接続するWi-FiのSSID名を選択して、詳細設定→プロキシ を「なし」から｢手動｣に設定します。
設定項目は以下のとおりです。
```
プロキシのホスト名: 先程調べたPCのIPアドレス

プロキシポート: 8888

プロキシをバイパス: (空欄)
```
設定後は保存をタップします。

１３，Charlesで Connection from ～と表示されるので、｢Accept｣を押します。
![](https://user-images.githubusercontent.com/52069677/164911402-8e4f994e-8871-4fea-9f5e-1b811dfd58ee.png)<br>
※ 10.0.0.8 の部分はチャレパ側のIPアドレスなので、環境により異なります。<br>
また、これが出てこなかった場合は どこか手順を間違えているか、パソコンやルーターの設定でセパレータが動作している可能性があります。

１４，チャレパ側で設定のトップに戻り、<br>
セキュリティと現在地情報→画面ロック に入ります。

パスワードが要求されるので、入力後「なし」を選択します。

１５，ホーム画面に戻り、スタートボタンを押します。

![](https://user-images.githubusercontent.com/52069677/164911100-959604e3-d1c9-4250-9b95-94fbb2b0de62.png)

１６，画面に従って初期設定を進めます。

１７，アップデートの赤い進捗バーが１００％になると以下のような画面が出ます。dcha_state を３にすることに成功しました。

![](https://user-images.githubusercontent.com/52069677/164909889-8afae15a-6e77-411c-8654-5fe9d614fb1d.png)

※ユーザー情報入力画面やエラーが出た場合は設定にミスがあります。戻って確認してください。

１８，ホームから設定を開き、以下のように開発者向けオプションを開きます。

設定→システム→詳細設定→タブレット情報→詳細設定→ビルド番号を７回タップ

1つ前の画面に戻り、開発者向けオプションを開く。

※パスワードを要求された場合は失敗です。１５番に戻ってください。

１９，USBデバッグをオンにします。

２０，PCで以下のアプリのapkファイルをダウンロードします。

CPad Customize Tool<br>
[https://github.com/Kobold831/CPadCustomizeTool/releases](https://github.com/Kobold831/CPadCustomizeTool/releases)

Aurora Store for NEO<br>
[https://github.com/Kobold831/AuroraStore4/releases](https://github.com/Kobold831/AuroraStore4/releases)

Nova Launcher<br>
[https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher](https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher)

２１，ダウンロードしたapkのあるディレクトリで以下のコマンドを実行します。
```
adb install それぞれのapkのファイル名

adb shell pm grant com.saradabar.cpadcustomizetool android.permission.WRITE_SECURE_SETTINGS

adb shell dpm set-device-owner com.saradabar.cpadcustomizetool/.Receiver.AdministratorReceiver

adb shell dpm set-active-admin com.aurora.store/.data.receiver.AdministratorReceiver

adb shell pm set-home-activity --user 0 com.teslacoilsw.launcher/.NovaLauncher
```
※adbコマンドがない場合はインストールしてください。入れ方は各自調べてください。

２２，チャレンジパッドのホームボタンを押します。

２３，Nova Launcherの初期設定をします。

２４，ランチャーからカスタマイズツールを開きます。

２５．システムUIを通常用に変更とナビゲーションバーの表示を維持をオンにします。

２６，これで改造完了です。

