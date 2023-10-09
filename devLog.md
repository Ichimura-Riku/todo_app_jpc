# 技術書展用のメモ

書きたい内容

- room実装
- hiltの導入と解説

## 下書き

### JetpackComposeのTodoアプリにDI（Hilt）を導入してみる

市村凌久

アプリ開発初心者の私がHiltの導入に挑戦しました。ドキュメントも少ない中でなんとか実装を進めていったので、参考になればと思います。

### DIとは

参考: https://the-simple.jp/what-is-di-dependency-injection-one-of-the-software-design-patterns

依存関係の注入のことです。依存関係とは、オブジェクト指向プログラミングにおける、クラスやインターフェースの継承やコンストラクタの参照渡しなどによって構築されます。こうした依存関係はアーキテクチャ設計に則ると、プログラムの仕様変更や機能追加によって影響を及ぼす範囲を狭められるほどいいとされています。具体的な例はandroidDevelopersのMVVMを説明したドキュメントに記載されています。クラスだと複雑なのでただの関数を例にすると、関数の中で変数を低意義するより、引数を使って処理を書いたほうが柔軟性があると思います。これのオブジェクト指向バージョンです。ただ、オブジェクト指向でこうした設計をするには非常に手間なことが多いです。その作業の一部を自動化させて楽しようというコンセプトがこのDIになります。

### 題材とするアプリ

分かりやすくAndroidDevelopersのドキュメントにあるRoomの実装を改造してHiltの導入を行っていきたいと思います。

# 実装ログ

最初に入れたい機能とアプリの概要を決めた

目標はリリースで高品質なアプリを目指した

現役エンジニアの方と相談しながらアプリで使用するライブラリの相談をした

基本的なレイアウトを先に作成して、roomの開発を進める

hiltのところで、オブジェクト指向の理解不足がかなり響いてきている

# roomの実装

tutorialを見ようみまねで実装しようとしたけど上手くいかなかったのでまずはtutorailの内容を丸写ししてそこから合わせていくことにした

並行してhilt, lintとかのツールを導入していった

これがだいぶ厄介で、難しいことを同時にやろうとしてかなりつまづいている

roomの実装自体はできて、あとは合わせていくだけ

この段階まで持っていくのにすら苦労した

## 実装面

### ViewModel内の`savedStateHandle`

このプロパティは一見意味がなさそうだが、画面回転した時のデータ保持なんかを担ってくれる便利屋なんで引数に入れる！

### **mutableStateOf**

uiStateの変更があったときにUIに反映させるための関数

```kotlin
var itemUiState by mutableStateOf(ItemUiState())
    private set

```

### Daoの`@Insert(onConflict = OnConflictStrategy.IGNORE)`

競合が発生した時にその処理を無視する記述

## 機能面

### provider

ここの部分をHiltにやってもらう感じになる。基本的にinterfaceとかを継承したクラスののコンストラクタを渡すために宣言している。目的はUI層との分離で、テストをやりやすくしたり機能の変更を簡単にしたりする目的があってわざわざproviderを入れている。そのまま書くならViewModelとかの内部にコンストラクタを生成すればいいんだけど、そうすると低レイヤーの変更がこうしたViewModelクラスまで変更していかないといけない。たとえば、コンストラクタの引数とかを変更すると一気に影響が出る。こうした問題を解決するためにproviderがあって、そのめんどくささを解消するためにHiltがある。

## エラー対処

### tutorialの実装したアプリ画面が出てこず、エラーでビルドはできるけど即終了する

Surfaceを入れるのを忘れていた

hiltの設定をミスっていた

### hiltが導入したが動かない

いくつかのフェーズがあり、それぞれでうまく設定できていないと動かせない

（そもそもhiltがdaggerの内包していて、わからない部分はdaggerを参考にして実装するとよかった。）

- hiltはkaptでしか動かせなかった（2023/09/06時点で、最新のアルファ版でようやくkspで動かせるようになったらしい。。。）。
- 現役エンジニアの方はAndroid developerのドキュメントではなく、daggerのドキュメントを参考にしていた
- kspが使えるところは使って、daggerだけkaptを併用する形で実装した
    - この際、kspとkaptのバージョンの組み合わせでうまく動かないものがあるので気をつける。自分の場合、

  `id("com.google.devtools.ksp") *version* "1.8.10-1.0.9" *apply*　false
  id("org.jetbrains.kotlin.kapt") *version* "1.9.１0"`
  の２つでエラーが発生したので、

  `id("com.google.devtools.ksp") *version* "1.9.0-1.0.13" *apply* false　id("org.jetbrains.kotlin.kapt") *version* "1.9.0"`

  と修正しました。

  でもどうせなら新しい機能を使ってみたい！（フラグ）

- 簡単な個人的理解として、interfaceの継承を自動でやってくれるためのもの
    - 便利になる点としては、テストコードを導入しやすくなったり、可読性が上がる（らしい）
- プラグインを手当たり次第に対応していた時のやつで`// kapt("groupId:artifactId:version")` のartifactId:
  versionが当たってしまった…

### hilt導入の個人的理解

1. Applicationクラスを作成して、`@HiltAndroidApp`をつける
2. 依存関係注入を行う所に`@AndroidEntryPoint`などのアノテーションを入れる
   （クラスによってアノテーションは変化するかも）
3. メインの使用用途はinterfaceの継承とコンストラクタをUIクラスに影響を与えない程度に分離させつつテストの汎用性を持たせる所になる。
4.
    3. のために、`@module` と`@InstallIn` のアノテーションをつけたmodule用のクラスを作成する
5. `@InstallIn` にはシングルトンクラスなどのアノテーション属性を指定する。
   → @InstallIn(SingletonComponent::class)など（singletonしか知らない…）

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideItemsRepository(provideContext: Context): ItemsRepository {
        return OfflineItemsRepository(InventoryDatabase.getDatabase(provideContext).itemDao())
    }
}
```

### daoのプロパティ型があってない

`/Users/ichimura/StudioProjects/todo_app_jpc/app/build/tmp/kapt3/stubs/debug/com/example/todo_app_jpc/data/TodoDao.java:19: エラー: Type of the parameter must be a class annotated with @Entity or a collection/array of it.
kotlin.coroutines.Continuation<? super java.lang.Void> $completion);`

試したこと

- @Query にsuspendをつける
- 関連定義ファイル全てでQueryをvoidに設定

あインストールで解決できる時がある

バージョン違いでなんかあるらしい

`@Database(entities = [Todo::class], version = 1, exportSchema = false)`

build > clean project

insert

解決！

kaptのバージョンが対応してなくてエラーが出た

1.8.10で動きました！

## コルーチンの処理を実装

コルーチンはスレッド処理でメインスレッドに影響を及ばさないような並列処理を実装してくれる

```kotlin

withContext(Dispatchers.IO) { <動作させたい処理や関数 > }
```

flowが返り値の時はこっちも有効

```kotlin
<該当する関数() > . flowOn (Dispatchers.IO)
```

### flowとワンショット（suspend）の使い分け

flowは連続的にデータが変化して、それをUIに反映させたい時に使うといいらしい

# 実装工程

- navigationの実装
    - graph
        - MyAppViewとMainBodyの違いと関係性について精査する
        - 一旦todoAppの構造から精査しないといけないかも
            - はじめはnavigationいらないと思って設計してた故のTodoApp
            - navigationが必要となったので、一旦はmainBodyとTodoAppの統一から始めた方がいいかも知れない
            - 多分、TodoAppの中身をMainBodyに入れればいいと思われる
            - めんどくさいポイントは、コード量が増えてること
            - 結果
                - mainBody
                    - homeBody
        - 変更点
            - MainActivityの起動関数変更
            - todoAppの画面に関するものはMainBodyScreenに移動
            - 状態管理変数を実装
            - viewmodelを引数にする必要がある
            - 元々呼び出されていた部分との差分があるかを確認する
            - mainbody ViewModelの移植作業
            - 軽く変えてみたけど、結局上手く行かないくさい
            - publicにしろ！的な文章が出てきたのでprivateなのが原因
                - 今は連結作業を進める
                - 連結はできた気がする
                - きた！ｳﾚｼｲ！
                - 依存関係整理してなんとかエラーがない状態を作れた
                - けど、クラッシュした！
                - やっぱりgetShowBottomSheetの関数とかが原因のようだ…
                - 何だか知らんが通った！
                - 原因はprivate変数なのに_がついてなかったことらしい。これつけたらビルドできた！
                - とりあえず進展。タスク一つ消化
                - コミットするか
            -
        - めンンンンどくさ
    - destination
    - mainActivity
    - homeのnavhost object
    - 動作確認
- detailの実装
    - ViewModelの連結をする
    - destinationの実装
        - barに適応
        - navigationに適応
        - クリック関数を追加
    - screen
        - navigationの処理を実装する
            - 書いた
            - ビルドエラー
            - navigationの部分でエラーが起きた
            - クリックイベントの部分がうまく指定できてないと仮定して確認する
            - 予想は引数をうまく指定してないんじゃないかっていう
            - そもそもなんであの書き方で動くのかよくわからんかったし、一回よくみてみるか
            - navGraphの方のcompose関数の引数がrouteだったので、routeIdArgsに変更したらなんか動いた！よくわからんが動いたからヨシ
    - viewModel(写すだけ）
- deleteの実装
    - 削除ボタンを作成
        - 戻る時のボタンも必要だった
        - とりあえずnavControllerを持たせた
        - https://tech.mokelab.com/android/compose/app/navigation/topAppBar.html
    - 確認画面を作成
        - 今detailとeditで分かれてるけど、これ一緒でいい気がする
        - 一旦、detailをeditに寄せるか？
        - 逆かもしれない
        - 逆にして、ビルドできるか試す？
        - 普通に消して名前変えた方が早そう
        - それでいく
        - 今、ViewModelに必要なものがいくつかあるからそれをまとめる
            - TextFieldに入力する内容を保持
                - stateを持てばいい
                - これは他から持ってきていいものなのかな？確認する
                    - 当たり
                - 多分できた
                - 画面回転にもしっかり対応してるんで大丈夫だと思う。

            - そういえばそもそもデータとってきてなかったわ
                - これやらんといけんね
                - データ取得はコメントアウトしてたやつを消してやる感じだと思う。
                - これ若干優先順位高めかも
            - OKしたときにRoomに保存
                - キタァ！！
                - 実装完了
                - いけてたはずだけど進まなかったのは、使う関数がupdateじゃなくてinsertだったから
            -
    - delete関数の実装
        - この作業に入る
        - topAppBarに入れる
        - あと、ボトムに入れる
            - 完了にしたっていうボタンも入れたい
        - 消す時、確認アラート入れる
- 編集の実装

あとで

- タイル長押しで削除も追加
- 登録する時の他の要素も追加できるようにする
- ボトムメニューバーに機能を持たせる
- detailのtitleをなんとかする