# Cpad\_dcha\_3\_changer

## 警告

このツールの使用によって起きた損害については保証しません｡

また､ `Cpad_dcha_3_changer`は､ **一時的に**開発者向けオプションのロックを回避するためのアプリです｡  
簡易セットアップ後も開発者向けオプションを利用する場合は､ [**BypassDchaLock**](https://github.com/s1204IT/BypassDchaLock) を使って下さい｡

## 必要なもの

*   チャレンジパッド Neo/Next
*   Windows, Linux, Mac OS搭載のPC
*   ADB環境
*   USB 2.0 Mini-B ケーブル

## 準備

Charlesを以下のURLからインストールします｡

[https://www.charlesproxy.com/download/](https://www.charlesproxy.com/download/)

無料版と有料版がありますが無料版で問題ありません｡  
無料版は３０分で強制終了されるのでその都度再起動してください｡  
設定は保存されるので手順を遡る必要はありません｡

## インストール

１，まずPCで **Charles** を開きます｡

![](https://user-images.githubusercontent.com/46545607/150069973-a1caa0d8-ec52-4cd9-8139-6860caa39fdb.png)

２，`Proxy`→`Proxy settings...`を開き､ 画像の通りに設定します｡

![](https://user-images.githubusercontent.com/46545607/150070449-2f8413de-e142-429e-886a-f1d40fd584da.png)

![](https://user-images.githubusercontent.com/46545607/150070448-0f03137a-52b5-4639-b78b-d48ae29ed0b5.png)

設定後､ `OK`を押します｡

３，`Proxy`→`SSL Proxying Settings...`を開き､ **Include**側の`Add`から画像の通りに設定します｡

![](https://user-images.githubusercontent.com/52069677/164899196-0ddab9ba-6dae-489a-ba90-b8b108ad7a29.png)

`Enable SSL Proxying`にチェックを入れます

![](https://user-images.githubusercontent.com/46545607/150072166-354e90b7-f560-4913-8af9-a7db4876e859.png)

設定後､ `OK`を押します｡

４，`Tools`→`Rewrite...`を開きます｡  
`Enable Rewrite`にチェックを入れ､ `Add`を押して`Untitled Set`を追加後､ 選択します｡
![](https://user-images.githubusercontent.com/46545607/150072490-991cc50a-9941-46b0-a795-0c2e254244e2.png)

５，下の画像の様な画面が出るので､ 上部の`Add`を押します｡
![](https://user-images.githubusercontent.com/46545607/150072831-16124fc9-641c-4610-9276-abaa24988852.png)

下の画像の通りに設定します｡  
![](https://user-images.githubusercontent.com/46545607/150072963-1fa59517-96c2-4eab-9ec3-3b216d343536.png)

設定値は次の通りです｡  

Protocol: `https`

Host:
```
townak.benesse.ne.jp
```

Port: `443`

Path:
```
rel/A/sp_84/open/TouchSetupLogin.apk
```

Query: (空欄)

設定後､ `OK`を押します｡

６，５の画像下部の`Add`を押します｡

![](https://user-images.githubusercontent.com/52069677/209759334-c6b0e806-a21c-4038-b314-9e3b494f9a68.png)

設定値は次の通りです｡

Type: `URL`

Match - Value:
```
https://townak.benesse.ne.jp/rel/A/sp_84/open/TouchSetupLogin.apk
```

Replace -  Value:
```
https://github.com/mouseos/Cpad_dcha_3_changer/releases/latest/download/app-release.apk
```

`Replace all`を選択

設定後､ OKを押します｡

７，`Help`→`SSL Proxying`→`Save Charles Root Certificate...`から､ ファイルのタイプを[`Binary certificate (.cer)`]にしてから証明書ファイルを保存します｡  
![](https://user-images.githubusercontent.com/52069677/164907250-9a3b0ebc-d761-412d-b573-50b41dc855b8.png)  
保存先は分かりやすい場所にしてください｡

８，`Help`→`Local IP Addresses`を開き､ PCのIPアドレスをメモします｡  
`192.168.`で始まるものが必要です｡

![](https://user-images.githubusercontent.com/52069677/205293725-fa181ca2-f946-4220-bbe9-2a41b1b81f0a.png)

この場合は`192.168.11.9`です｡

９，チャレンジパッドとPCをUSB接続します｡  
チャレンジパッドの通知領域に｢`Androidシステム・この端末を USB で充電中`｣と表示されているので､ ダブルタップしてUSBの設定を開きます｡
![](https://user-images.githubusercontent.com/52069677/164910761-156ca2b0-11e3-4f51-9832-1ea2128c834b.png)  

｢`USB の使用`｣の項目で｢`ファイル転送`｣を選択してください｡  
![](https://user-images.githubusercontent.com/52069677/164909609-7326b7d8-7b8d-4a6b-8ec8-0fa649316f3e.png)

１０，PCのエクスプローラーにチャレンジパッドが表示されます｡  
先程保存した証明書ファイルを､ [`TAB-A05-BD(BA1)`→`内部共有ストレージ`]にコピーしてください｡

１１，チャレンジパッドの設定アプリから次の通りに開きます｡

`ネットワークとインターネット`→`Wi-Fi`→`Wi-Fi 設定`→`詳細設定`→`証明書のインストール` を開きます｡

左側のメニューを開き､ `TAB-A05-BD`もしくは`TAB-A05-BA1`を開きます｡

先程コピーした証明書ファイルがあるので選択します｡

`証明書の名前を指定する`と言ったダイアログが開くので､ 適当に名前を付けて`OK`を押します｡  
｢`VPNとアプリ`｣のまま変更しないでください｡

パスワード設定を要求されるので設定します｡

１２，証明書がインストール出来たら１つ前の画面に戻り､ `Wi-Fi の使用` を有効にします｡ 
次に､ 接続するWi-FiのSSID名を選択して､ `詳細設定`→`プロキシ` を｢`なし`｣から｢`手動`｣に設定します｡ 
設定項目は以下の通りです｡  

プロキシのホスト名: 先程調べたPCのIPアドレス

プロキシポート: `8888`

プロキシをバイパス: (空欄)

設定後､ `保存`をタップします｡

１３，Charlesで `Connection from ～`と表示されるので､ ｢Allow｣を押します｡
![](https://user-images.githubusercontent.com/52069677/164911402-8e4f994e-8871-4fea-9f5e-1b811dfd58ee.png)  
※ `10.0.0.8` の部分はチャレパ側のIPアドレスなので､ 環境により異なります｡  
また､ これが出てこなかった場合は どこか手順を間違えているか､ パソコンやルーターの設定で**セパレータ**が動作している可能性があります｡  
詳しくは[こちら](Separator.md)をご覧下さい｡

１４，チャレパ側で設定のトップに戻り､  
`セキュリティと現在地情報`→`画面ロック`に入ります｡

パスワードが要求されるので､ 入力後｢`なし`｣を選択します｡

１５，ホーム画面に戻り､ スタートボタンを押します｡

![](https://user-images.githubusercontent.com/52069677/164911100-959604e3-d1c9-4250-9b95-94fbb2b0de62.png)

１６，画面に従って初期設定を進めます｡

※バッテリー残量が50%未満だと続行できません｡

１７，アップデートの赤い進捗バーが１００％になると以下のような画面が出ます｡  

![](https://user-images.githubusercontent.com/52069677/205294809-2761d67d-d6b3-44bb-a60b-df02bedc9cc0.png)

**DchaState** を３にすることに成功しています｡  
※ユーザー情報入力画面やエラーが出た場合は設定にミスがあります｡ 戻って確認してください｡

１８，ホームから設定アプリを開き､ 以下の通りに開発者向けオプションを開きます｡

`システム`→`詳細設定`→`タブレット情報`→`詳細設定`→`ビルド番号`を７回タップ

1つ前の画面に戻り､ `開発者向けオプション`を開く｡

※パスワードを要求された場合は失敗です｡ １５番に戻ってください｡

１９，`USBデバッグ`をオンにします｡

２０，PCで以下のADBコマンドを実行します｡
```
adb shell pm uninstall --user 0 jp.co.benesse.dcha.dchaservice
```

２１，PCで以下のアプリのAPKファイルをダウンロードします｡

Nova Launcher  
[https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher](https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher)

２２，ダウンロードしたAPKのあるディレクトリで以下のコマンドを実行します｡
```
adb install .\NovaLauncher_[version].apk
```
```
adb shell pm set-home-activity --user 0 com.teslacoilsw.launcher/.NovaLauncher
```

２３，チャレンジパッドのホームボタンを押します｡

２４，Nova Launcherの初期設定をします｡

２５，これで改造完了です｡
