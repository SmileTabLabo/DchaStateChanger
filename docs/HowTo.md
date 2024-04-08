# DchaState Changer の使い方

## 警告

> [!CAUTION]
> このツールの使用によって起きた損害については保証しません｡

## お知らせ

> [!TIP]
> Charles を使用せずに microSDカードを使用して改造できる [**Nova Direct Installer**](https://github.com/SmileTabLabo/NovaDirectInstaller) がリリースされました｡  
是非､ そちらも併せてご確認下さい｡

---

## 必要なもの

- チャレンジパッド Neo/Next
- Charles を実行できるPC
  このページでは､ **Windows** の前提で解説します｡
- ADB 環境
- USB 2.0 Mini-B ケーブル

## 準備

Charlesを以下のURLからインストールします｡

<https://www.charlesproxy.com/download/>  
無料版は３０分で強制終了されるのでその都度再起動してください｡  
設定は保存されるので手順を遡る必要はありません｡

## インストール

１，まずPCで **Charles** を開きます｡

[![](https://user-images.githubusercontent.com/52069677/223978260-88cb73c3-fc5c-4e71-9c90-080206a7bcb5.png)](#)

> [!NOTE]
> ※初回起動時にファイアウォールの設定の画面が出たら､ ｢`プライベート ネットワーク`｣に必ずチェックを入れてから､  
> ｢<kbd><b>アクセスを許可する(A)</b></kbd>｣を押してください｡  
> [![](https://user-images.githubusercontent.com/52069677/223978231-a80befe8-531e-44f9-8361-d2316e4741f8.png)](#)

２，次のリンクを開き､ ファイルとして保存します｡  
[**Settings.xml**](Settings.xml?raw=true)  
右クリックして｢<kbd>名前を付けて保存...</kbd>｣  
[![](https://user-images.githubusercontent.com/52069677/223977340-bf346a16-d8fb-4cec-aea0-16447a142f6b.png)](#)

３，<kbd>Tools</kbd>→<kbd>Import/Export Settings...</kbd> から <kbd>Choose File</kbd>を選択し､ ２番で保存した **Settings.xml** を選択します｡  
[![](https://user-images.githubusercontent.com/52069677/224062723-8286164b-3b16-489a-baa9-1c5e758cdba2.png)](#)

ファイルを選択出来たら､ 下部の<kbd>Import</kbd>を押します｡

４，<kbd>Help</kbd>→<kbd>SSL Proxying</kbd>→<kbd>Save Charles Root Certificate...</kbd>から､ ファイルのタイプを <kbd>Binary certificate (.cer)</kbd> にしてから証明書ファイルを保存します｡  
[![](https://user-images.githubusercontent.com/52069677/164907250-9a3b0ebc-d761-412d-b573-50b41dc855b8.png)](#)
保存先は分かりやすい場所にしてください｡

５，<kbd>Help</kbd>→<kbd>Local IP Addresses</kbd>を開き､ PCのIPアドレスをメモします｡  
通常は､ `192.168.`で始まるものが必要です｡

[![](https://user-images.githubusercontent.com/52069677/205293725-fa181ca2-f946-4220-bbe9-2a41b1b81f0a.png)](#)

この場合は`192.168.11.9`です｡

６，チャレンジパッドとPCをUSB接続します｡  
チャレンジパッドの通知領域に｢<kbd>Androidシステム・この端末を USB で充電中</kbd>｣と表示されているので､ ダブルタップしてUSBの設定を開きます｡  
[![](https://user-images.githubusercontent.com/52069677/164910761-156ca2b0-11e3-4f51-9832-1ea2128c834b.png)](#)

｢`USB の使用`｣の項目で｢<kbd>ファイル転送</kbd>｣を選択してください｡  
[![](https://user-images.githubusercontent.com/52069677/164909609-7326b7d8-7b8d-4a6b-8ec8-0fa649316f3e.png)](#)

７，PCのエクスプローラーにチャレンジパッドが表示されます｡  
先程保存した証明書ファイルを､ <kbd>TAB-A05-BD(BA1)</kbd>→<kbd>内部共有ストレージ</kbd> にコピーしてください｡

８，チャレンジパッドの設定アプリから次の通りに開きます｡

<kbd>ネットワークとインターネット</kbd>→<kbd>Wi-Fi</kbd>→<kbd>Wi-Fi 設定</kbd>→<kbd>詳細設定</kbd>→<kbd>証明書のインストール</kbd> を開きます｡

左側のメニューを開き､ <kbd>TAB-A05-BD</kbd>もしくは<kbd>TAB-A05-BA1</kbd>を開きます｡

先程コピーした証明書ファイルがあるので選択します｡

`証明書の名前を指定する`と言ったダイアログが開くので､ 適当に名前を付けて<kbd>OK</kbd>を押します｡  

> [!WARNING]
> ｢<kbd>VPNとアプリ</kbd>｣のまま変更しないでください｡

パスワード設定を要求されるので設定します｡  
> [!WARNING]
> ※この際､ ｢`起動時の保護`｣は｢<kbd><b>いいえ</b></kbd>｣を選択してください｡  
｢パターン｣を選択した際､ 再起動時に解除できなくなります｡
[![](https://github.com/SmileTabLabo/DchaStateChanger/assets/52069677/c59af69d-7fed-40e6-b661-b897e2e3b6da)](#)

９，証明書がインストール出来たら１つ前の画面に戻り､ <kbd>Wi-Fi の使用</kbd> を有効にします｡  
次に､ 接続するWi-FiのSSID名を選択して､ <kbd>詳細設定</kbd>→<kbd>プロキシ</kbd> を｢<kbd>なし</kbd>｣から｢<kbd>手動</kbd>｣に設定します｡ 
設定項目は以下の通りです｡  

> プロキシのホスト名: 先程調べたPCのIPアドレス
>
> プロキシポート: `8888`
>
> プロキシをバイパス: (空欄)

設定後､ <kbd>保存</kbd>をタップします｡

１０，Charlesで `Connection from ～`と表示されるので､ ｢<kbd>Allow</kbd>｣を押します｡  
[![](https://user-images.githubusercontent.com/52069677/164911402-8e4f994e-8871-4fea-9f5e-1b811dfd58ee.png)](#)  
※ `10.0.0.8` の部分はチャレパ側のIPアドレスなので､ 環境により異なります｡  
> [!WARNING]
> また､ これが出てこなかった場合は どこか手順を間違えているか､ パソコンやルーターの設定で**セパレータ**が動作している可能性があります｡  
詳しくは[こちら](Separator.md)をご覧下さい｡

１１，ホーム画面に戻り､ スタートボタンを押します｡

[![](https://user-images.githubusercontent.com/52069677/164911100-959604e3-d1c9-4250-9b95-94fbb2b0de62.png)](#)

１２，画面に従って初期設定を進めます｡

> [!WARNING]
> ※USB または ACアダプターが接続されていないと続行できません｡  
また､ バッテリー残量が50%未満だと続行できません｡  

１３，アップデートの赤い進捗バーが１００％になり正常に続行すると以下のような画面が出ます｡  

[![](https://github.com/SmileTabLabo/DchaStateChanger/assets/52069677/88e9874c-00f5-44d4-b03c-1787b3b54bbb)](#)  
｢<kbd><b>この端末管理アプリを有効にする</b></kbd>｣を選択してください｡

[![](https://github.com/SmileTabLabo/DchaStateChanger/assets/52069677/b094c6b7-2573-413e-8204-0993dbccfe4b)](#)  
この画面になったら､ ホームボタンを押します｡

<details><summary><b>｢このアプリをアンインストールしてください｣</b>と出た場合</summary>

<p><a href="#"><img src="https://github.com/SmileTabLabo/DchaStateChanger/assets/52069677/fc780b12-5754-4be8-bc8c-531ab71b7dc4" alt="" /></a><br />
この様に出た場合は､ このアプリを実行する必要はありません｡<br />
アプリをアンインストールし､ 次のステップへ進んでください｡</p>

<hr />

</details>

> [!WARNING]
> ※ユーザー情報入力画面やエラーが出た場合は設定にミスがあります｡ 戻って確認してください｡  
また､ 連続で失敗するとエラーを吐き続ける場合があるので､ その際は一度初期化してから再度試みてください｡  

１４，ホームから設定アプリを開き､ 以下の通りに開発者向けオプションを開きます｡  

<kbd>システム</kbd>→<kbd>詳細設定</kbd>→<kbd>タブレット情報</kbd>→<kbd>詳細設定</kbd>→<kbd>ビルド番号</kbd>を７回タップ

1つ前の画面に戻り､ <kbd>開発者向けオプション</kbd>を開く｡

１５，<kbd>USBデバッグ</kbd>をオンにし､ PCで以下のADBコマンドを実行します｡

```
adb shell settings put system dcha_state 0
```
```
adb shell pm uninstall --user 0 jp.co.benesse.dcha.dchaservice
```
```
adb shell pm uninstall --user 0 jp.co.benesse.dcha.setupwizard
```

これで改造の準備は完了です｡

---

### NovaLauncher のインストール方法

PCで **Nova Launcher** のAPKファイルをダウンロードします｡

Nova Launcher  
<https://teslacoilapps.com/tesladirect/download.pl?packageName=com.teslacoilsw.launcher&versionCode=70058>

ダウンロードしたAPKのあるディレクトリで以下のコマンドを実行します｡
```
adb install -g -i com.android.vending NovaLauncher_7.0.58.apk
```
```
adb shell pm set-home-activity --user 0 com.teslacoilsw.launcher/.NovaLauncher
```

ホームボタンを押すと､ Nova Launcher が起動するので､ 画面に従って初期設定を行ってください｡
