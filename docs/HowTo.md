# DchaState Changer の使い方

## 警告

このツールの使用によって起きた損害については保証しません｡

## 必要なもの

- チャレンジパッド Neo/Next
  - チャレンジパッド３は対象外です。
    https://wiki3.jp/SmileTabLabo/page/21#count_dcha_completed
- Windows, Linux, Mac OS搭載のPC
- ADB環境
- USB 2.0 Mini-B ケーブル

## 準備

Charlesを以下のURLからインストールします｡

[https://www.charlesproxy.com/download/](https://www.charlesproxy.com/download/)

無料版と有料版がありますが無料版で問題ありません｡  
無料版は３０分で強制終了されるのでその都度再起動してください｡  
設定は保存されるので手順を遡る必要はありません｡

## インストール

１，まずPCで **Charles** を開きます｡

![](https://user-images.githubusercontent.com/52069677/223978260-88cb73c3-fc5c-4e71-9c90-080206a7bcb5.png)

※初回起動時にファイアウォールの設定の画面が出たら､ ｢プライベートネットワーク｣を必ず許可してください｡
![](https://user-images.githubusercontent.com/52069677/223978231-a80befe8-531e-44f9-8361-d2316e4741f8.png)

２，次のリンクを開き､ ファイルとして保存します｡  
<https://raw.githubusercontent.com/mouseos/Cpad_dcha_3_changer/HEAD/docs/Settings.xml>  
右クリックして｢名前を付けて保存...｣  
![](https://user-images.githubusercontent.com/52069677/223977340-bf346a16-d8fb-4cec-aea0-16447a142f6b.png)  

３，`Tools`→`Import/Export Settings...` から `Choose File`を選択し､ ２番で保存した **Settings.xml** を選択します｡  
![](https://user-images.githubusercontent.com/52069677/224062723-8286164b-3b16-489a-baa9-1c5e758cdba2.png)

ファイルを選択出来たら､ 下部の`Import`を押します｡

４，`Help`→`SSL Proxying`→`Save Charles Root Certificate...`から､ ファイルのタイプを[`Binary certificate (.cer)`]にしてから証明書ファイルを保存します｡  
![](https://user-images.githubusercontent.com/52069677/164907250-9a3b0ebc-d761-412d-b573-50b41dc855b8.png)  
保存先は分かりやすい場所にしてください｡

５，`Help`→`Local IP Addresses`を開き､ PCのIPアドレスをメモします｡  
`192.168.`で始まるものが必要です｡

![](https://user-images.githubusercontent.com/52069677/205293725-fa181ca2-f946-4220-bbe9-2a41b1b81f0a.png)

この場合は`192.168.11.9`です｡

６，チャレンジパッドとPCをUSB接続します｡  
チャレンジパッドの通知領域に｢`Androidシステム・この端末を USB で充電中`｣と表示されているので､ ダブルタップしてUSBの設定を開きます｡
![](https://user-images.githubusercontent.com/52069677/164910761-156ca2b0-11e3-4f51-9832-1ea2128c834b.png)  

｢`USB の使用`｣の項目で｢`ファイル転送`｣を選択してください｡  
![](https://user-images.githubusercontent.com/52069677/164909609-7326b7d8-7b8d-4a6b-8ec8-0fa649316f3e.png)

７，PCのエクスプローラーにチャレンジパッドが表示されます｡  
先程保存した証明書ファイルを､ [`TAB-A05-BD(BA1)`→`内部共有ストレージ`]にコピーしてください｡

８，チャレンジパッドの設定アプリから次の通りに開きます｡

`ネットワークとインターネット`→`Wi-Fi`→`Wi-Fi 設定`→`詳細設定`→`証明書のインストール` を開きます｡

左側のメニューを開き､ `TAB-A05-BD`もしくは`TAB-A05-BA1`を開きます｡

先程コピーした証明書ファイルがあるので選択します｡

`証明書の名前を指定する`と言ったダイアログが開くので､ 適当に名前を付けて`OK`を押します｡  
｢`VPNとアプリ`｣のまま変更しないでください｡

パスワード設定を要求されるので設定します｡

９，証明書がインストール出来たら１つ前の画面に戻り､ `Wi-Fi の使用` を有効にします｡ 
次に､ 接続するWi-FiのSSID名を選択して､ `詳細設定`→`プロキシ` を｢`なし`｣から｢`手動`｣に設定します｡ 
設定項目は以下の通りです｡  

プロキシのホスト名: 先程調べたPCのIPアドレス

プロキシポート: `8888`

プロキシをバイパス: (空欄)

設定後､ `保存`をタップします｡

１０，Charlesで `Connection from ～`と表示されるので､ ｢Allow｣を押します｡  
![](https://user-images.githubusercontent.com/52069677/164911402-8e4f994e-8871-4fea-9f5e-1b811dfd58ee.png)  
※ `10.0.0.8` の部分はチャレパ側のIPアドレスなので､ 環境により異なります｡  
また､ これが出てこなかった場合は どこか手順を間違えているか､ パソコンやルーターの設定で**セパレータ**が動作している可能性があります｡  
詳しくは[こちら](Separator.md)をご覧下さい｡

１１，チャレパ側で設定のトップに戻り､  
`セキュリティと現在地情報`→`画面ロック`に入ります｡

パスワードが要求されるので､ 入力後｢`なし`｣を選択します｡

１２，ホーム画面に戻り､ スタートボタンを押します｡

![](https://user-images.githubusercontent.com/52069677/164911100-959604e3-d1c9-4250-9b95-94fbb2b0de62.png)

１３，画面に従って初期設定を進めます｡

※バッテリー残量が50%未満だと続行できません｡

１４，アップデートの赤い進捗バーが１００％になり正常に続行すると以下のような画面が出ます｡  
![](https://user-images.githubusercontent.com/52069677/223981470-0474e9d7-1f38-474a-ab5a-66b3a92bddc6.png)

文章は異なりますが､ **DchaState** を３にすることに成功しています｡  
※ユーザー情報入力画面やエラーが出た場合は設定にミスがあります｡ 戻って確認してください｡
また､ 連続で失敗するとエラーを吐き続ける場合があるので､ その際は一度初期化してから再度試みてください｡

１５，ホームから設定アプリを開き､ 以下の通りに開発者向けオプションを開きます｡
※ホーム画面を開いた後､ 再度ウィジェットに触れないように注意してください｡  

`システム`→`詳細設定`→`タブレット情報`→`詳細設定`→`ビルド番号`を７回タップ

1つ前の画面に戻り､ `開発者向けオプション`を開く｡

※パスワードを要求された場合は失敗です｡ １２番に戻ってください｡

１６，`USBデバッグ`をオンにし､ PCで以下のADBコマンドを実行します｡
```
adb shell pm uninstall --user 0 jp.co.benesse.dcha.dchaservice
```
```
adb shell settings put system dcha_state 0
```

１７，PCで **Nova Launcher** のAPKファイルをダウンロードします｡

Nova Launcher  
<https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher&versionCode=70057>

１８，ダウンロードしたAPKのあるディレクトリで以下のコマンドを実行します｡
```
adb install .\NovaLauncher_7.0.57.apk
```
```
adb shell pm set-home-activity --user 0 com.teslacoilsw.launcher/.NovaLauncher
```

１９，チャレンジパッドのホームボタンを押します｡

２０，Nova Launcherの初期設定をします｡

これで改造完了です｡