たたらクラフト
===

ゆづことMOD以前に手探りでつくっていたMODです。  
履歴を残す意味で保存しています。

Minecraft 1.8.9  
Minecraft Forge 1.8.9-11.15.1.1722  

---

## このMODについて
テスト実装したアイテム・ブロック・エンチャントなどが雑多に含まれています。  

- 切り替え式一括破壊系マルチツールのドリル
- 設置できるリュック的なインベントリアイテム
- 1種類のアイテムを大量にストックできるアイテム
- スポナー回収のエンチャント
- 経験値収納本

などなど

## 構築手順（Windows/Eclipse）
1. リポジトリをローカルへ展開（ダウンロード or Git）
1. コマンドラインで展開した場所へ
1. gradlew setupDecompWorkspace
1. gradlew eclipse
1. 失敗したら gradlew clean 後にやり直す
1. Eclipseへインポート
1. 実行構成「Java アプリケーション -> プロジェクト名_Client」を設定すればEclipse上で実行できる
1. gradlew build でbuild/lib下にjarファイルができあがる