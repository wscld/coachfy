package co.wscld.coachfy.Data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import co.wscld.coachfy.Helper;
import co.wscld.coachfy.Objects.Exercicio;
import co.wscld.coachfy.Objects.Historico;
import co.wscld.coachfy.Objects.Tag;
import co.wscld.coachfy.Objects.Treino;
import co.wscld.coachfy.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class StorageManager {
    Context context;
    RealmConfiguration config;
    public StorageManager(Context context){
        this.context = context;
        Realm.init(context);
        config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public ArrayList<Drawable> getTreinoImages(){
        ArrayList images = new ArrayList<>();

        images.add(R.drawable.treino1);
        images.add(R.drawable.treino2);
        images.add(R.drawable.treino3);
        images.add(R.drawable.treino4);
        images.add(R.drawable.treino5);
        return images;
    }

    public ArrayList<Treino> getTreinos(Tag tag){
        ArrayList<Treino> treinos = new ArrayList<>();
        Realm realm = Realm.getInstance(config);
        RealmResults<Treino> result;
        if(tag == null){
            result = realm.where(Treino.class).findAll();
        }else{
            result = realm.where(Treino.class).equalTo("tag",tag.getId()).findAll();
        }
        treinos.addAll(realm.copyFromRealm(result));
        realm.close();
        return treinos;
    }

    public Treino getTreino(String treinoId){
        Treino treino = new Treino();
        Realm realm = Realm.getInstance(config);
        treino = realm.copyFromRealm(realm.where(Treino.class).equalTo("id",treinoId).findFirst());
        realm.close();
        return treino;
    }

    public ArrayList<Historico> getHistorico(){
        ArrayList<Historico> historicos = new ArrayList<>();
        Realm realm = Realm.getInstance(config);
        historicos.addAll(realm.copyFromRealm(realm.where(Historico.class).findAll()));
        realm.close();
        return historicos;
    }

    public ArrayList<Tag> getTags(){
        createGeneralTag();
        ArrayList<Tag> tags = new ArrayList<>();
        Realm realm = Realm.getInstance(config);
        RealmResults<Tag> results = realm.where(Tag.class).findAll();
        tags.addAll(realm.copyFromRealm(results));
        realm.close();

        return tags;
    }

    public ArrayList<Exercicio> getExercicios(String treinoId){
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        Realm realm = Realm.getInstance(config);
        RealmResults<Exercicio> result = realm.where(Exercicio.class).equalTo("treinoId",treinoId).findAll();
        exercicios.addAll(realm.copyFromRealm(result));
        realm.close();
        return exercicios;
    }

    public int getFocoTreino(String treinoId){
        ArrayList<Integer> grupos = new ArrayList<>();
        Realm realm = Realm.getInstance(config);
        RealmResults<Exercicio> result = realm.where(Exercicio.class).equalTo("treinoId",treinoId).findAll();
        for(Exercicio exercicio : result){
            grupos.add(exercicio.getGrupoMuscular());
        }
        realm.close();
        if(grupos.size() == 0 || grupos == null){
            return 0;
        }else{
            return Helper.mostCommon(grupos);
        }
    }

    public void createTreino(String nome, String descricao, String tagId, int iconId){
        Realm realm = Realm.getInstance(config);

        Treino treino = new Treino();
        treino.setId(UUID.randomUUID().toString());
        treino.setNome(nome);
        treino.setDescricao(descricao);
        treino.setTagId(tagId);
        treino.setIconId(iconId);

        realm.beginTransaction();
        realm.copyToRealm(treino);
        realm.commitTransaction();
        realm.close();
    }

    public void deleteTreino(String id){
        Realm realm = Realm.getInstance(config);
        Treino treino = realm.where(Treino.class).equalTo("id",id).findFirst();
        RealmResults<Exercicio> exercicios = realm.where(Exercicio.class).equalTo("treinoId",id).findAll();

        realm.beginTransaction();
        treino.deleteFromRealm();
        exercicios.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public void createExercicio(String treinoId, String nome, int grupoMuscular,String detalhes, int carga, int repeticoes, int series){
        Realm realm = Realm.getInstance(config);

        Exercicio exercicio = new Exercicio();
        exercicio.setId(UUID.randomUUID().toString());
        exercicio.setTreinoId(treinoId);
        exercicio.setCarga(carga);
        exercicio.setDetalhes(detalhes);
        exercicio.setGrupoMuscular(grupoMuscular);
        exercicio.setNome(nome);
        exercicio.setRepeticoes(repeticoes);
        exercicio.setSeries(series);

        realm.beginTransaction();
        realm.copyToRealm(exercicio);
        realm.commitTransaction();
        realm.close();
    }

    public void deleteExercicio(String id){
        Realm realm = Realm.getInstance(config);

        Exercicio exercicio = realm.where(Exercicio.class).equalTo("id",id).findFirst();

        realm.beginTransaction();
        if(exercicio != null){
            exercicio.deleteFromRealm();
        }
        realm.commitTransaction();
        realm.close();
    }

    public void createHistorico(String treinoId){
        Realm realm = Realm.getInstance(config);
        Historico historico = new Historico();
        historico.setDate(new Date().getTime());
        historico.setId(UUID.randomUUID().toString());
        historico.setTreinoId(treinoId);
        realm.beginTransaction();
        realm.copyToRealm(historico);
        realm.commitTransaction();
        realm.close();
    }

    public void deleteHistorico(String id){
        Realm realm = Realm.getInstance(config);
        Historico historico = realm.where(Historico.class).equalTo("id",id).findFirst();
        realm.beginTransaction();
        if(historico != null) {
            historico.deleteFromRealm();
        }
        realm.commitTransaction();
        realm.close();
    }

    public void createTag(String name){
        Realm realm = Realm.getInstance(config);
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID().toString());
        tag.setName(name);
        realm.beginTransaction();
        realm.copyToRealm(tag);
        realm.commitTransaction();
        realm.close();
    }

    public void createGeneralTag(){
        Realm realm = Realm.getInstance(config);
        if(realm.where(Tag.class).equalTo("id","0").findFirst() == null) {
            Tag tag = new Tag();
            tag.setId("0");
            tag.setName("Geral");
            realm.beginTransaction();
            realm.copyToRealm(tag);
            realm.commitTransaction();
            realm.close();
        }
    }

    public void deleteTag(String id){
        Realm realm = Realm.getInstance(config);
        Tag tag = realm.where(Tag.class).equalTo("id",id).findFirst();
        realm.beginTransaction();
        if(tag != null){
            tag.deleteFromRealm();
        }
        realm.commitTransaction();
        realm.close();
    }
}
